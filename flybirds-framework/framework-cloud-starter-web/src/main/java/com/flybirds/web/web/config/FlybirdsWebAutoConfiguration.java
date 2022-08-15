package com.flybirds.web.web.config;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.common.constant.Constant;
import com.flybirds.web.web.core.handler.GlobalExceptionHandler;
import com.flybirds.web.web.core.handler.GlobalResponseBodyHandler;
import com.flybirds.web.web.core.interceptor.AuthorizationInterceptor;
import com.flybirds.web.web.core.interceptor.NoRepeatSubmitInterceptor;
import com.flybirds.web.web.core.interceptor.OauthServiceSignatureInterceptor;
import com.flybirds.web.web.core.resolver.LoginUserHandlerMethodArgumentResolver;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * web-mvc的配置操作
 *
 * @author flybirds
 */
@Configuration
@EnableConfigurationProperties({XssProperties.class})
public class FlybirdsWebAutoConfiguration implements WebMvcConfigurer {

    /**
     * html静态资源   js静态资源    css静态资源
     */
    private final List<String> staticResources = Lists.newArrayList("/**/*.html",
            "/**/*.js",
            "/**/*.css",
            "/**/*.woff",
            "/**/*.ttf");

    /**
     * 应用名
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 注解解析用户的凭证数据
     * {@link com.flybirds.web.web.annotation.Login}
     */
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    /**
     * 服务之间认证，防止外部故意破坏访问
     * {@link com.flybirds.web.web.annotation.OauthServiceSignature}
     */
    @Autowired
    private OauthServiceSignatureInterceptor oauthServiceSignatureInterceptor;

    /**
     * 接口幂等性-不允许重复提交
     */
    @Autowired
    private NoRepeatSubmitInterceptor noRepeatSubmitInterceptor;

    /**
     * 登录成功之后，{@link com.flybirds.web.web.annotation.LoginUser} 注解获取用户的信息
     */
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    /**
     * 注意【新旧版本SpringBoot配置文件 WebMvcConfigurationSupport类 和 WebMvcConfigurer接口之间有冲突。】
     * 地址：https://blog.csdn.net/jianxia801/article/details/114288604
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置knife4j 显示文档
         */
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        /**
         * 配置swagger-ui显示文档
         */
        registry.addResourceHandler("/statics/**")
                .addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler(applicationName);
    }

    @Bean
    public GlobalResponseBodyHandler globalResponseBodyHandler() {
        return new GlobalResponseBodyHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有路径都被拦截
        registry.addInterceptor(noRepeatSubmitInterceptor).addPathPatterns("/**").excludePathPatterns(staticResources);
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**").excludePathPatterns(staticResources);

        //todo 其他服务在这里依次添加
        if (ObjectUtil.equal(applicationName, Constant.CloudServiceName.FLYBIRDS_SYSTEM)) {
            registry.addInterceptor(authorizationInterceptor)
                    .addPathPatterns("/user/info/**",
                                     "/user/check/**",
                                     "/api/**",
                                     "/logininfor",
                                     "/operlog").excludePathPatterns(staticResources);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }

}
