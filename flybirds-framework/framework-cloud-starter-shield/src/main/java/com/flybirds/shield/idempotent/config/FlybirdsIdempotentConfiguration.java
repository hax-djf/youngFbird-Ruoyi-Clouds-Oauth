package com.flybirds.shield.idempotent.config;

import com.flybirds.shield.idempotent.core.aop.IdempotentAspect;
import com.flybirds.shield.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.flybirds.shield.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.flybirds.shield.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.flybirds.shield.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class FlybirdsIdempotentConfiguration {

    /***
     * 初始化idempotentAspect切面，配置一些列的IdempotentKeyResolver解析器，方便直接可以通过
     * @param keyResolvers
     * @param idempotentRedisDAO
     * @return
     */
    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
