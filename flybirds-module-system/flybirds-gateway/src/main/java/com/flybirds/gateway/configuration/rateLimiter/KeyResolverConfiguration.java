package com.flybirds.gateway.configuration.rateLimiter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author :flybirds
 * @create :2021-07-17 14:06:00
 * @description :spring Clouds 网关结合redsi 限流配置。
 *               主要正对于主机host_ip与路由限流，以及qps规则进行限定，核心思想在于令牌牌的规则，数据存储在redsi以key value的形式。
 *               限流成功，返回 HTTP ERROR 429
 *               配置规则见文档flybirds-gateway.md
 * 注意事项:
 *               规则只能存在一个否则会出新一下错误
 * org.springframework.cloud.gateway.config.GatewayAutoConfiguration required a single bean, but 3 were found:

 */
@Configuration
public class KeyResolverConfiguration {

    //路由URI限流：key-resolver: "#{@pathKeyResolver}"
    @Bean
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }


    //iP限流：key-resolver: "#{@ipKeyResolver}"
//    @Bean
//    public KeyResolver ipKeyResolver()
//    {
//        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
//    }

    //参数限流：key-resolver: "#{@parameterKeyResolver}"
//    @Bean
//    public KeyResolver parameterKeyResolver()
//    {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("flybirds"));
//    }

}
