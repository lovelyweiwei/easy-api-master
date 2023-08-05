package com.weiwei.easyapigateway.filter;

import com.weiwei.common.model.entity.InterfaceInfo;
import com.weiwei.common.model.entity.User;
import com.weiwei.common.model.entity.UserInterfaceInfo;
import com.weiwei.common.service.InnerInterfaceInfoService;
import com.weiwei.common.service.InnerUserInterfaceInfoService;
import com.weiwei.common.service.InnerUserService;
import com.weiwei.easyapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 全局过滤
 *
 * @Author weiwei
 * @Date 2023/6/16 12:13
 * @Version 1.0
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @DubboReference(version = "1.0.0", check = false)
    private InnerUserService innerUserService;

    @DubboReference(version = "1.0.0", check = false)
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference(version = "1.0.0", check = false)
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    //public static final String INTERFACE_HOST = "http://localhost:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.请求日志
        ServerHttpRequest request = exchange.getRequest();
        // todo
        //String path = INTERFACE_HOST + request.getPath().value();
        String path = request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        log.info("请求来源地址：" + request.getRemoteAddress());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        ServerHttpResponse response = exchange.getResponse();
        // 2.访问控制--黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            return handleNoAuth(response);
        }
        // 3.用户鉴权 (判断 ak.sk 是否合法)
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = null;
        try {
            body = URLDecoder.decode(headers.getFirst("body"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String sign = headers.getFirst("sign");
        String source = headers.getFirst("source");
        // 实际情况是去数据库中查找
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }
        if (Long.parseLong(nonce) > 10000) {
            return handleNoAuth(response);
        }
        // 流量染色，避免绕过网关请求
        if (!"gateway".equals(source)) {
            return handleNoAuth(response);
        }
        // 时间和当前时间不能超过5分钟  防重放攻击
        Long currentTime = System.currentTimeMillis() / 1000;
        Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) > FIVE_MINUTES) {
            return handleNoAuth(response);
        }
        // 5分钟内 防重放攻击
        if (nonce != null) {
            // 判断是否重复请求
            String ak = stringRedisTemplate.opsForValue().get(nonce);
            if (ak != null) {
                return handleNoAuth(response);
            } else {
                stringRedisTemplate.opsForValue().set(nonce, accessKey, 5, TimeUnit.MINUTES); //5分钟后timestamp会拦截超过5分钟的请求
                System.out.println(stringRedisTemplate.opsForValue().get(nonce));
            }
        }
        // 实际情况是从数据库中查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.getSign(body, secretKey, nonce, timestamp);
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }
        // 4.请求的模拟接口是否存在?
        // 从数据库中查询模拟接口是否存在，以及请求方法是否匹配
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInvokeInterface error", e);
        }
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }
        // 是否还有调用次数
        UserInterfaceInfo userInterfaceInfo = innerUserInterfaceInfoService.getById(interfaceInfo.getId(), invokeUser.getId());
        // 如果没有对应调用接口数据
        if (userInterfaceInfo == null) {
            int i = innerUserInterfaceInfoService.addDefault(interfaceInfo.getId(), invokeUser.getId());
            if (i <= 0) {
                return handleInvokeError(response);
            }
        }
        if (userInterfaceInfo.getLeftNum() <= 0) {
            return handleInvokeError(response);
        }
        // 5.请求转发.调用模拟接口
        //Mono<Void> filter = chain.filter(exchange);
        //return filter;
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

}
