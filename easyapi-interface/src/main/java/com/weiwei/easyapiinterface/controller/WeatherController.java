package com.weiwei.easyapiinterface.controller;

import cn.hutool.http.HttpUtil;
import com.weiwei.easyapiclientsdk.model.DomainNameParams;
import com.weiwei.easyapiclientsdk.model.WeatherParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author weiwei
 * @Date 2023/8/5 20:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @PostMapping("/")
    public String getWeatherByPost(@RequestBody(required = false) WeatherParams weatherParams, HttpServletRequest request) throws Exception {
        String weatherUrl = "https://api.vvhan.com/api/weather";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", weatherParams.getCity());
        String result = HttpUtil.get(weatherUrl, paramMap);
        return result;
    }
}
