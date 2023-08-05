package com.weiwei.easyapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import com.weiwei.easyapiclientsdk.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * 调用第三方接口的客户端
 *
 * @Author weiwei
 * @Date 2023/6/13 21:16
 * @Version 1.0
 */
public class EasyApiClient {

    public static String GATEWAY_HOST = "http:/localhost:8090";

    public void setGATEWAY_HOST(String gateway_Host) {
        this.GATEWAY_HOST = gateway_Host;
    }

    private String accessKey;

    private String secretKey;

    public EasyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    //public String getNameByGet(String name){
    //    //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
    //    HashMap<String, Object> paramMap = new HashMap<>();
    //    paramMap.put("name", name);
    //
    //    String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);  // 对方提供的不是https服务的端口
    //    //System.out.println(result);
    //    return result;
    //}
    //
    //public String getNameByPost(String name){
    //    //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
    //    HashMap<String, Object> paramMap = new HashMap<>();
    //    paramMap.put("name", name);
    //
    //    String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
    //    //System.out.println(result);
    //    return result;
    //}

    private Map<String, String> getHeaderMap(String body) {
        if (body == null) {
            body = "zw";
        }
        String encode = null;
        try {
            encode = URLEncoder.encode(body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> hasMap = new HashMap<>();
        String nonce = RandomUtil.randomNumbers(4);
        String currentTime = String.valueOf(System.currentTimeMillis() / 1000);
        hasMap.put("accessKey", accessKey);
        hasMap.put("nonce", nonce);
        hasMap.put("body", encode);
        hasMap.put("timestamp", currentTime);
        hasMap.put("source", "gateway"); //流量染色
        hasMap.put("sign", SignUtils.getSign(body, secretKey, nonce, currentTime));
        return hasMap;
    }

    //public String getUsernameByPost(UserParams user){
    //    String json = JSONUtil.toJsonStr(user);
    //    HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
    //            .addHeaders(getHeaderMap(json))
    //            .body(json)
    //            .execute();
    //    System.out.println(httpResponse.getStatus());
    //    //System.out.println(httpResponse.body());
    //    return httpResponse.body();
    //}

    //public String getAvatarUrl(AvatarParams avatarParams){
    //    String parameters = JSON.toJSONString(avatarParams);
    //    String result = onlineInvoke(parameters, "/api/avatar/avatarUrl");
    //    return result;
    //}
    //
    //public String getBaiduInfo(BaiduParams baiduParams){
    //    String parameters = JSON.toJSONString(baiduParams);
    //    String result = onlineInvoke(parameters, "/api/baidu/baiduInfo");
    //    return result;
    //}

    public String onlineInvoke(String parameters, String url) {
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + url)
                .addHeaders(getHeaderMap(parameters))
                .body(parameters)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        return result;
    }
}
