package com.weiwei.easyapiinterface.controller;

import cn.hutool.http.HttpUtil;
import com.weiwei.easyapiclientsdk.model.BaiduParams;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @ClassName BaiduController
 * @Description TODO
 * @Author lish
 * @Date 2023/5/2 19:57
 */

@RestController
@RequestMapping("/baidu")
public class BaiduController {
    @PostMapping("/baiduInfo")
    public String getBaiduInfoByPost(@RequestBody(required = false) BaiduParams baiduParams, HttpServletRequest request) {
    //public String getBaiduInfoByPost(BaiduParams baiduParams, HttpServletRequest request) {
        //https://restapi.amap.com/v3/weather/weatherInfo?
        String baiduUrl = "https://www.coderutil.com/api/resou/v1/baidu";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access-key", "2a73055beafb826cf0aaf0d284d9eede");
        paramMap.put("secret-key", "3fe196bd0a439eef303155b3870b71d5");
        if (baiduParams == null) {
            baiduParams = new BaiduParams();
        }

        if (baiduParams.getSize() == null ||  "".equals(baiduParams.getSize())) {
            paramMap.put("size", 10);
        }else {
            paramMap.put("size", baiduParams.getSize());
        }
        String result = HttpUtil.get(baiduUrl, paramMap);
        return result;
    }

}
