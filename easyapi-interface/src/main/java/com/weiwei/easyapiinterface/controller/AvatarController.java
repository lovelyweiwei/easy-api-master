package com.weiwei.easyapiinterface.controller;

import com.weiwei.easyapiclientsdk.model.AvatarParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName AvatarController
 * @Description TODO
 * @Author lish
 * @Date 2023/5/9 0:37
 */
@RestController
@RequestMapping("/avatar")
@Slf4j
public class AvatarController {


    @PostMapping("/avatarUrl")
    public String getAvatarUrlByPost(@RequestBody(required = false) AvatarParams avatarParams, HttpServletRequest request) throws Exception {
    //public String getAvatarUrlByPost(AvatarParams avatarParams, HttpServletRequest request) throws Exception {
        //https://restapi.amap.com/v3/weather/weatherInfo?
        // https 不能是http 否则拿不到图片
        String avatarUrl = "https://www.loliapi.com/acg/pp/";
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("type", "json");
//        paramMap.put("tx", "b1");
//        paramMap.put("key", "2yta7ZzxPfP6YqZZLzQi413D3B");
//        HttpRequest get = HttpUtil.createGet(avatarUrl, true);
        String redirectUrl = getRedirectUrl(avatarUrl);
        log.info(redirectUrl);
        return redirectUrl;
    }


    /**
     * 获取重定向地址
     * @param path
     * @return
     * @throws Exception
     */
    private String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path)
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        String location = conn.getHeaderField("Location");
        return location;
    }

}
