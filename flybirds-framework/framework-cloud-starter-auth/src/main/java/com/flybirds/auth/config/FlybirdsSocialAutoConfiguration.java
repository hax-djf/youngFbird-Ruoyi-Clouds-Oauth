package com.flybirds.auth.config;

import com.flybirds.auth.core.FlybirdsAuthRequestFactory;
import com.xkcoding.justauth.autoconfigure.JustAuthProperties;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 社交自动装配类
 *
 * @author timfruit
 * @date 2021-10-30
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(JustAuthProperties.class)
public class FlybirdsSocialAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "justauth", value = "enabled", havingValue = "true", matchIfMissing = true)
    public FlybirdsAuthRequestFactory FlybirdsAuthRequestFactory(JustAuthProperties properties, AuthStateCache authStateCache) {
        return new FlybirdsAuthRequestFactory(properties, authStateCache);
    }

}
