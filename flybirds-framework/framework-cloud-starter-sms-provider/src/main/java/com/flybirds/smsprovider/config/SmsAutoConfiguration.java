package com.flybirds.smsprovider.config;

import com.flybirds.smsprovider.factory.SmsClientFactory;
import com.flybirds.smsprovider.factory.SmsClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信配置类
 *
 * @author 芋道源码
 */
@Configuration
public class SmsAutoConfiguration {

    /**
     * 项目启动的时候，开始自动装配各个短信渠道客户端 smsClient
     * @return
     */
    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
