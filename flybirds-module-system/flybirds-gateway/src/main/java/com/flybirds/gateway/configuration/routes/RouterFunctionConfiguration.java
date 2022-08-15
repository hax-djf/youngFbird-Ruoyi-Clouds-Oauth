package com.flybirds.gateway.configuration.routes;

import com.flybirds.gateway.handler.verification.VerificationCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * 路由配置信息 {控制器方法用于处理重定向的/code的请求}
 * 
 * @author ruoyi
 */
@Configuration
public class RouterFunctionConfiguration {

    @Autowired
    private VerificationCodeHandler validateCodeHandler;

    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions.route(
                RequestPredicates.GET("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                validateCodeHandler);
    }
}
