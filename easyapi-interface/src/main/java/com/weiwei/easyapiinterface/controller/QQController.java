package com.weiwei.easyapiinterface.controller;

import cn.hutool.http.HttpUtil;
import com.weiwei.easyapiclientsdk.model.DomainNameParams;
import com.weiwei.easyapiclientsdk.model.QQParams;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author weiwei
 * @Date 2023/6/18 15:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/qq")
public class QQController {

    @PostMapping("/isOnline")
    public String getQQIsOnlineByPost(@RequestBody(required = false) QQParams qqParams, HttpServletRequest request) throws Exception {
        String qqUrl = "http://api.btstu.cn/qqol/api.php";
        HashMap<String, Object> paramMap = new HashMap<>();
        if (qqParams == null) {
            qqParams = new QQParams();
        }
        if (qqParams.getQq() == null || "".equals(qqParams.getQq())) {
            return "{\"error\": \"参数不能为空\"}";
        }else {
            paramMap.put("qq", qqParams.getQq());
        }
        String result = HttpUtil.get(qqUrl, paramMap);
        return result;
    }
}
