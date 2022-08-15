package com.flybirds.security.config;

import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.security.handler.CustomAccessDeniedHandler;
import com.flybirds.security.handler.CustomAuthExceptionEntryPoint;
import com.flybirds.security.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * 授权服务配置
 *
 * @author flybirds
 */
@Configuration
@EnableResourceServer // 开启资源服务器(标识他是一个oauth2中的资源服务器)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //激活方法上的PreAuthorize注解
@Order(2) //不能改动
@SuppressWarnings("all")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String PUBLIC_KEY = "public.key";

    @Value("${spring.application.name}")
    String applicationName;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    CustomAuthExceptionEntryPoint customAuthExceptionEntryPoint;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    DataSource dataSource2;

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new CustomTokenStore(redisConnectionFactory, this.dataSource2);
        tokenStore.setPrefix(CacheConstantEnum.OAUTH_ACCESS.getKey());
        boolean isOss = true;
        if (isOss) {
            tokenStore.setAuthenticationKeyGenerator(new UserAuthenticationKeyGenerator());
        }
        return tokenStore;
    }

    /**
     * oauth_client_token 表数据
     *
     *{@link org.springframework.security.oauth2.client.token.ClientTokenServices  这张表数据}
     */
    @Bean
    public JdbcClientTokenServices clientTokenServices() {
        return new JdbcClientTokenServices(this.dataSource2);
    }

    // 定义JwtAccessTokenConverter  授权jwt装换器
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        //注意必须设置 Cannot convert access token to JSON
        converter.setVerifier(new RsaVerifier(getPubKey()));
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {
            @Override
            public Authentication extractAuthentication(Map<String, ?> map) {
                /**
                 * 弃用自定义缓存形式从redis存储数据，改为直接tokenStore时使用jdk序列化操作获取数据
                 */
                Authentication authentication = super.extractAuthentication(map);

                Collection<? extends GrantedAuthority> authorities = getAuthorities(map);

                OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(SecurityUtils.getToken());

                LoginUser principal = (LoginUser) oAuth2Authentication.getPrincipal();

                return new UsernamePasswordAuthenticationToken(principal,
                        authentication.getCredentials(),  authorities == null ? authentication.getAuthorities():authorities);
            }
        });
        converter.setAccessTokenConverter(tokenConverter);
        return converter;
    }

    /**
     * 获取权限资源信息
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(SecurityConstant.AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        DefaultTokenServices tonkenService = new DefaultTokenServices();
        tonkenService.setTokenStore(tokenStore());
        resources.tokenServices(tonkenService);
        //异常处理
        resources.authenticationEntryPoint(customAuthExceptionEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);

        super.configure(resources);
    }

    //获取公钥
    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }

    // Http安全配置，对每个到达系统的http请求链接进行校验
    @Override
    public void configure(HttpSecurity http) throws Exception {

        switch (applicationName){
            case Constant.CloudServiceName.FLYBIRDS_SYSTEM:
                http.authorizeRequests()
                    .antMatchers(
                            "/user/open/**",
                            "/social/open/**", //社交登录
                            "/tenant/open/**",
                            "/operlog",
                            "/logininfor/add/**",
                            "/api/**",
                            "/register/**",
                            "/retrieve/**",
                            "/druid/**",
                            "/errorCode/remote/**",
                            "/actuator/**",
                            "/v2/api-docs",
                            "/favicon.ico")
                .permitAll()
                .anyRequest()
                .authenticated() //其他地址需要认证授权
                // 允许frame 篡改域名地址  https://blog.csdn.net/kejizhentan/article/details/113729051
                .and()
                .headers().frameOptions().sameOrigin();
                break;
            case Constant.CloudServiceName.FLYBIRDS_SMS:
                http.authorizeRequests()
                        .antMatchers(
                                "/druid/**",
                                "/actuator/**",
                                "/v2/api-docs",
                                "/favicon.ico",
                                "/send/**")
                        .permitAll().anyRequest().authenticated()
                        .and()
                        .headers().frameOptions().sameOrigin();
                break;
            case Constant.CloudServiceName.FLYBIRDS_OAUTH:
                http.authorizeRequests()
                        .antMatchers(
                                "/oauth-system/**",
                                "/api/**",
                                "/register/**",
                                "/retrieve/**",
                                "/druid/**",
                                "/errorCode/remote/**",
                                "/actuator/**",
                                "/v2/api-docs",
                                "/favicon.ico")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                        .and()
                        .headers().frameOptions().sameOrigin();
                break;
            default:
                http.authorizeRequests()
                        .antMatchers(
                                "/druid/**",
                                "/actuator/**",
                                "/v2/api-docs",
                                "/favicon.ico")
                        .permitAll().anyRequest().authenticated()
                        .and()
                        .headers().frameOptions().sameOrigin();
        }

    }
}
