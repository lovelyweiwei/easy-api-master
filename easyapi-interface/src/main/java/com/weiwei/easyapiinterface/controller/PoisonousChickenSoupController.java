package com.weiwei.easyapiinterface.controller;

import cn.hutool.http.HttpUtil;
import com.weiwei.easyapiclientsdk.model.AvatarParams;
import com.weiwei.easyapiclientsdk.model.BaiduParams;
import com.weiwei.easyapiclientsdk.model.ChickenSoupParams;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 毒鸡汤
 *
 * @Author weiwei
 * @Date 2023/6/18 14:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/chickenSoup")
public class PoisonousChickenSoupController {

    @PostMapping("/text")
    public String getAvatarUrlByPost(@RequestBody(required = false) ChickenSoupParams chickenSoupParams, HttpServletRequest request) throws Exception {
    //public String getChickenSoupByPost(ChickenSoupParams chickenSoupParams, HttpServletRequest request) throws Exception {
        String chickenSoupUrl = "http://api.btstu.cn/yan/api.php";
        HashMap<String, Object> paramMap = new HashMap<>();
        if (chickenSoupParams == null) {
            chickenSoupParams = new ChickenSoupParams();
        }

        if (chickenSoupParams.getCharset() == null || "".equals(chickenSoupParams.getCharset())) {
            paramMap.put("charset", "utf-8");
        }else {
            paramMap.put("charset", chickenSoupParams.getCharset());
        }
        if (chickenSoupParams.getEncode() == null || "".equals(chickenSoupParams.getEncode())) {
            paramMap.put("encode", "text");
        }else {
            paramMap.put("charset", chickenSoupParams.getEncode());
        }
        String text = HttpUtil.get(chickenSoupUrl, paramMap);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{\"text\": \"");
        stringBuffer.append(text);
        stringBuffer.append("\"}");
        return stringBuffer.toString();
    }
}
