package com.flybirds.sms.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * FlybirdsSystemServerConfig 配置文件
 *
 * @author flybirds
 */
@Configuration
public class FlybirdsSystemServerConfig {

    // RestTemplate 配置
    @Bean
    @LoadBalanced //开启负载均衡
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }
}
