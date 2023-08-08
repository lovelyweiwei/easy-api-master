package com.weiwei.easyapiinterface.controller;

import cn.hutool.http.HttpUtil;
import com.weiwei.easyapiclientsdk.model.DomainNameParams;
import com.weiwei.easyapiclientsdk.model.IPParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author weiwei
 * @Date 2023/6/18 15:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/ip")
public class IPController {

    @PostMapping("/search")
    public String getTiKuByPost(@RequestBody(required = false) IPParams ipParams, HttpServletRequest request) throws Exception {
        String IPUrl = "https://api.vvhan.com/api/getIpInfo";
        HashMap<String, Object> paramMap = new HashMap<>();
        if (ipParams == null) {
            ipParams = new IPParams();
        }
        if (ipParams.getIp() == null || "".equals(ipParams.getIp())) {
            return "{\"error\": \"参数不能为空\"}";
        } else {
            paramMap.put("ip", ipParams.getIp());
        }
        paramMap.put("ip", ipParams.getIp());
        String result = HttpUtil.get(IPUrl, paramMap);
        return result;
    }
}
