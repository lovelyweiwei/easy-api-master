package com.weiwei.easyapiinterface.controller;

import com.weiwei.easyapiclientsdk.model.UserParams;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称 API
 * @Author weiwei
 * @Date 2023/6/13 17:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name){
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name){
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody UserParams userParams, HttpServletRequest request){
        if (userParams.getUsername() == null || "".equals(userParams.getUsername())) {
            return "{\"error\": \"参数不能为空\"}";
        }
        //String result =  "POST 用户名字是" + userParams.getUsername();
        //return result;
        return "{\"username\": \"" + userParams.getUsername() + "\"}";
    }
}
