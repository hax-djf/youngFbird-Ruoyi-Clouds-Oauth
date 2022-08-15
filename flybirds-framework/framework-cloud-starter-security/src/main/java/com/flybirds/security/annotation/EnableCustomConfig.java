package com.flybirds.security.annotation;

import com.flybirds.security.config.ApplicationConfig;
//import com.flybirds.security.feign.OAuth2FeignConfig;
import com.flybirds.security.feign.OAuth2FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.flybirds.**.mapper")
// 开启线程异步执行
@EnableAsync
// 自动加载类
@Import({ApplicationConfig.class ,OAuth2FeignConfig.class})
public @interface EnableCustomConfig
{

}

