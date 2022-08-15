package com.flybirds.oauth.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.flybirds.oauth.handler.SentinelExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@Order(1) //不能改动
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico");
    }

    /* 初始化认证管理对象*/
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    /* PasswordEncoder 加密 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* RestTemplate限流规则 */
    @Bean
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
    public RestTemplate restTemplate() {
        RestTemplate  restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(180000); //单位为ms
        factory.setConnectTimeout(5000); //单位为ms
        return factory;
    }

    /** 初始化认证放行对象 */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /**
         *  保护URL常用的方法有：
             authenticated() 保护URL，需要用户登录
             permitAll() 指定URL无需保护，一般应用与静态资源文件
             hasRole(String role) 限制单个角色访问，角色将被增加 “ROLE_” .所以”ADMIN” 将和 “ROLE_ADMIN”进行比较.
             hasAuthority(String authority) 限制单个权限访问
             hasAnyRole(String… roles)允许多个角色访问.
             hasAnyAuthority(String… authorities) 允许多个权限访问.
             access(String attribute) 该方法使用 SpEL表达式, 所以可以创建复杂的限制.
             hasIpAddress(String ipaddressExpression) 限制IP地址或子网
         */
//            http
//                .cors().and() // 开启跨域
//                .csrf().disable() // CSRF 禁用，因为不使用 Session
//                .httpBasic()  //启用Http基本身份验证
//                .and()
//                .formLogin() //启用表单身份验证
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds(securityProperties.getRememberMeSeconds())//设置记住我的时间
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 基于 token 机制，所以不需要 Session
//                .and()
//                .authorizeRequests()  // 设置每个请求的权限
//                    .antMatchers("/r/r1").hasAnyAuthority("p1")
//                    .antMatchers("/token/**","/login*").permitAll()
//                    .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll() // 静态资源，可匿名访问
//                    .antMatchers("/infra/file/get/**").anonymous() // 文件的获取接口，可匿名访问
//                    .antMatchers("/swagger-ui.html").anonymous() // Swagger 接口文档
//                    .antMatchers("/swagger-resources/**").anonymous()
//                    .antMatchers("/webjars/**").anonymous()
//                    .antMatchers("/*/api-docs").anonymous()
//                    .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() // Spring Boot Admin Server 的安全配置 TODO 需要抽象出去
//                    .antMatchers("/rsa/publicKey","/actuator/**").permitAll()
//                    .antMatchers("/actuator").anonymous() // Spring Boot Actuator 的安全配置
//                    .antMatchers("/actuator/**").anonymous()
//                    .antMatchers("/druid/**").anonymous()  // Druid 监控 TODO 需要抽象出去
//                    .antMatchers("/system/sms/callback/**").anonymous() // 短信回调 API TODO 需要抽象出去
//                    // 除上面外的所有请求全部需要鉴权认证
//                    .anyRequest().authenticated()
//                .and()
//                .headers().frameOptions().disable();

            http
                .cors() // 开启跨域
                .and()
                .csrf().disable() // CSRF 禁用，因为不使用 Session
                .formLogin()  //启用表单身份验证
                .loginPage("/oauth-login.html") //自定义的登录页面 **重要**
                .loginProcessingUrl("/login")  //原始的处理登录的URL,保持和base-login.html的form表单的action一致 **重要**
                .permitAll() //放开 **重要**
                .and()
                .httpBasic() ///启用Http基本身份验证
                .and().authorizeRequests()
                .antMatchers("/r/r1").hasAnyAuthority("p1")
                .antMatchers("/token/**","/oauth-system/**","/login*").permitAll()
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll() // 静态资源，可匿名访问
                .antMatchers("/swagger-ui.html").anonymous() // Swagger 接口文档
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() // Spring Boot Admin Server 的安全配置 TODO 需要抽象出去
                .antMatchers("/rsa/publicKey","/actuator/**").permitAll()
                .antMatchers("/actuator").anonymous() // Spring Boot Actuator 的安全配置
                .antMatchers("/actuator/**").anonymous()
                .antMatchers("/druid/**").anonymous()  // Druid 监控 TODO 需要抽象出去
                .antMatchers("/system/sms/callback/**").anonymous() // 短信回调 API TODO 需要抽象出去
                .anyRequest().authenticated().and().headers().frameOptions().disable();

    }
}
