package com.weiwei.easyapiclientsdk;

import com.weiwei.easyapiclientsdk.client.EasyApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author weiwei
 * @Date 2023/6/14 16:21
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties("easyapi.client")
@Data
@ComponentScan
public class EasyApiClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public EasyApiClient easyApiClient(){
        return new EasyApiClient(accessKey, secretKey);
    }
}
