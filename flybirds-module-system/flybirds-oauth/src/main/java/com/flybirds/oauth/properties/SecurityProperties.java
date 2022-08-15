package com.flybirds.oauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author :flybrids
 * @create :2021-07-16 12:09:00
 * @description : SecurityProperties 配置类
 */
@Component
@Data
@ConfigurationProperties(prefix = "flybirds.security")
@PropertySource("classpath:bootstrap.yml")
public class SecurityProperties {

    /**
     * 记住我的有效时间秒
     */
    private int rememberMeSeconds;

    private OauthPageProperties oauthLogin = new OauthPageProperties();

}
