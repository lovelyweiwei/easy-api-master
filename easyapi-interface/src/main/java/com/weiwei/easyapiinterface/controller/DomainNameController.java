package com.weiwei.easyapiinterface.controller;

import cn.hutool.http.HttpUtil;
import com.weiwei.easyapiclientsdk.model.BaiduParams;
import com.weiwei.easyapiclientsdk.model.ChickenSoupParams;
import com.weiwei.easyapiclientsdk.model.DomainNameParams;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author weiwei
 * @Date 2023/6/18 15:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/domain")
public class DomainNameController {

    @PostMapping("/name")
    public String getDomainNameByPost(@RequestBody(required = false) DomainNameParams domainNameParams, HttpServletRequest request) throws Exception {
        String domainUrl = "http://api.btstu.cn/dmreg/api.php";
        HashMap<String, Object> paramMap = new HashMap<>();
        if (domainNameParams == null) {
            domainNameParams = new DomainNameParams();
        }

        if (domainNameParams.getDomain() == null || "".equals(domainNameParams.getDomain())) {
            return "{\"error\": \"参数不能为空\"}";
        }else {
            paramMap.put("domain", domainNameParams.getDomain());
        }
        String result = HttpUtil.get(domainUrl, paramMap);
        //StringBuffer stringBuffer = new StringBuffer();
        //stringBuffer.append("{\"text\": \"");
        //stringBuffer.append(result);
        //stringBuffer.append("\"}");
        //return stringBuffer.toString();
        return result;
    }
}
