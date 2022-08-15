package com.flybirds.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlusConfig 配置
 *
 * @author flybirds
 */
@Configuration
public class MybatisPlusConfig {

    /*
     * sql打印
     * @return
     */
//    @Bean
//    public SqlPrintInterceptor sqlPrintInterceptor(){
//        return new SqlPrintInterceptor();
//    }

//    @Bean
//    public ParameterInterceptorPlugin parameterInterceptorPlugin(){
//        return new ParameterInterceptorPlugin();
//    }

    /**
     * 3.4.2版本配置分页插件/乐观锁
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //sql打印使用mybatis_plus自带的
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}