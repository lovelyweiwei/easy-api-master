package com.weiwei.project.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static com.weiwei.easyapiclientsdk.client.EasyApiClient.GATEWAY_HOST;

/**
 * @Author weiwei
 * @Date 2023/6/15 20:32
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {
        boolean r = userInterfaceInfoService.invokeCount(1, 1);
        Assertions.assertTrue(r);
    }

    //@Test
    //public void test(){
    //    EasyApiClient easyApiClient = new EasyApiClient("vcerhypbd0a21af601635545429e6111", "bttrq4249c458ba2fg34a4a1c6adaw24");
    //    HttpResponse httpResponse = ((HttpRequest)HttpRequest.post("http://localhost:8123/api/avatar/avatarUrl").addHeaders(easyApiClient.getHeaderMap(""))).body("").execute();
    //    System.out.println(httpResponse.getStatus());
    //    String result = httpResponse.body();
    //    System.out.println(result);
    //}
}
