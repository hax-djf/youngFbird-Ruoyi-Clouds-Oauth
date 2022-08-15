package com.flybirds.oauth.config;

import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.oauth.adapter.approval.CustomApprovalStore;
import com.flybirds.oauth.adapter.authorizationCode.CustomJdbcAuthorizationCodeServices;
import com.flybirds.oauth.adapter.authorizationCode.CustomStateKeyGenerator;
import com.flybirds.oauth.adapter.granter.ResourceOwnerSmscodeTokenGranter;
import com.flybirds.oauth.adapter.granter.ResourceOwnerSocialcodeTokenGranter;
import com.flybirds.oauth.core.RedisClientDetailsService;
import com.flybirds.oauth.exception.CustomWebResponseExceptionTranslator;
import com.flybirds.security.model.LoginUser;
import com.flybirds.security.service.UserTokenServiceManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.filter.state.StateKeyGenerator;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.*;

/**
 *  配置认证服务
 * 
 *  @author flybirds
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    //认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;

    @Autowired
    private RedisClientDetailsService clientDetailsService;

    @Autowired
    private UserTokenServiceManger userTokenService;

    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private ApprovalStore approvalStore;

    /***
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在，这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     *  clientId：（必须的）用来标识客户的Id。
     *  secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     *  scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     *  authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     *  authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     * @param clients {@link ClientDetailsServiceConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }


    /**
     * 自定义客户端配置 ClientDetails实现【实现数据库redis同时缓存】
     *
     * @return {@link RedisClientDetailsService}
     */
    public RedisClientDetailsService clientDetailsService() {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        return clientDetailsService;
    }

    /***
     * 用来配置令牌（token）的访问端点和令牌服务(tokenservices)
     * AuthorizationServerEndpointsConfigurer 这个配置对象有一个叫做 pathMapping() 的方法用来配置端点URL链接，它有两个参数：
     *           第一个参数：String 类型的，这个端点URL的默认链接。
     *           第二个参数：String 类型的，你要进行替代的URL链接。
     *            /oauth/authorize：授权端点。
     *            /oauth/token：令牌端点。
     *            /oauth/confirm_access：用户确认授权提交端点。
     *            /oauth/error：授权服务错误信息端点。
     *            /oauth/check_token：用于资源服务访问的令牌解析端点。
     *            /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
     * @param endpoints {@link AuthorizationServerEndpointsConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .pathMapping("/oauth/confirm_access", "/oauth/confirmAccess") //授权确认页面
                .userDetailsService(userDetailsService) // 用户账号密码认证
                .authenticationManager(authenticationManager) // 指定认证管理器
                .authorizationCodeServices(authorizationCodeServices) //授权码存储到数据库
                .approvalStore(approvalStore) //确认授权阈值存储
                .reuseRefreshTokens(false) // 是否重复使用 refresh_token
                .exceptionTranslator(customWebResponseExceptionTranslator) // 自定义异常处理
                .tokenServices(tokenService()); //设置tokenService
        endpoints.tokenGranter(tokenGranter(endpoints)); //自定义授权模式
    }

    /***
     * * 用来配置令牌端点的安全约束
     *  （1）允许表单认证
     *  （2）密码的加密方式
     *  （3）第四行和第三行分别是允许已授权用户访问 checkToken 接口和获取 token 接口。
     *   tokenkey这个endpoint当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，这里指这个endpoint完全公开。
     * @param oauthServer {@link AuthorizationServerSecurityConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

        oauthServer.allowFormAuthenticationForClients() //允许表单认证
                .passwordEncoder(passwordEncoder) //密码的加密方式
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }


    /**
     * 配置生成jwt的格式令牌 {@link } 这主要是设置RS256 非对称加密
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        /**
         * JWT令牌由三部分组成
         * Header 哈希算法（如HMAC SHA256或RSA）
         * Payload 第二部分是负载，内容也是一个json对象，它是存放有效信息的地方，它可以存放jwt提供的现成字段，
         * 比如：iss（签发者）,exp（过期时间戳）, sub（面向的用户）等
         * Signature  第三部分是签名
         */
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyProperties.getKeyStore().getLocation(),                          //证书路径 flybirds.jks
                keyProperties.getKeyStore().getSecret().toCharArray())              //证书秘钥 flybirds
                .getKeyPair(
                        keyProperties.getKeyStore().getAlias(),                     //证书别名 flybirds
                        keyProperties.getKeyStore().getPassword().toCharArray());   //证书密码 flybirds
        converter.setKeyPair(keyPair);
        /**
         * {@link JwtAccessTokenConverter} 中可设置
         * DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter()
         * accessTokenConverter.setUserTokenConverter();可以设置自定义jwt自定义装换操作
         */
        return converter;
    }

    /**
     * {@link AuthorizationServerTokenServices} tokenService 将 {@link AuthorizationServerEndpointsConfigurer} 中的设置到token中
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices(); //创建默认tokenSerice
        service.setSupportRefreshToken(true); //开启令牌刷新
        service.setTokenStore(tokenStore); //设置tokenStore令牌存储器
        service.setClientDetailsService(clientDetailsService); //设置客户端 如果设置，默认的clientDetailsService 会失效，需要直接设置客户端的令牌过期时间等，否则走默认数据
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); // 创建令牌增强剂链
        /** 将 {@link TokenEnhancer} 增强和 {@link JwtAccessTokenConverter} 增强设置到链条中 */
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain); //设置到tokenService链条中
        return service;
    }

    /**
     * 配置授权码自定义存储和code生成操作
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new CustomJdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 授权阈 持久化存储
     */
    @Bean
    public ApprovalStore approvalStore(){
        return new CustomApprovalStore(dataSource);
    }

    /**
     * 接口暴露配置 KeyPair 获取公钥配
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("flybirds.jks"), "flybirds".toCharArray());
        return keyStoreKeyFactory.getKeyPair("flybirds", "flybirds".toCharArray());
    }

    /**
     * 读取秘钥的配置
     */
    @Bean(name = "keyProp")
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }

    /**
     * {@link TokenEnhancer 令牌增强剂 }
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (authentication.getUserAuthentication() != null) {
                Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
                LoginUser loginUser = (LoginUser) authentication.getUserAuthentication().getPrincipal();

                additionalInformation.put(SecurityConstant.DETAILS_USER_ID, loginUser.getUserId());
                additionalInformation.put(SecurityConstant.DETAILS_USERNAME, loginUser.getUsername());
                additionalInformation.put(SecurityConstant.DETAILS_MOBILE, loginUser.getSysUser().getPhonenumber()); //手机号
                additionalInformation.put(SecurityConstant.DETAILS_ONLINE_NUMBER, loginUser.getSysUser().getOnlineNumber()); //用户登录的数量
                additionalInformation.put(SecurityConstant.DETAILS_TENANT_ID, loginUser.getSysUser().getTenantId()); //租户id
                additionalInformation.put(SecurityConstant.OAUTH_USER_AGENT, loginUser.getUserAgent());
                additionalInformation.put(SecurityConstant.OAUTH_USER_IP, loginUser.getUserIp());
                additionalInformation.put(SecurityConstant.OAUTH_TOKEN_SECRETKEY, userTokenService.getRandomSecretKey()); //生成随机秘钥

                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
            }
            return accessToken;
        };
    }

    /**
     * Grant_type模式列表自定义扩展  {@link AuthorizationServerEndpointsConfigurer}  {@link TokenGranter}
     */
    public TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> list = new ArrayList<>();
        if (authenticationManager != null) {
            list.add(new ResourceOwnerPasswordTokenGranter(authenticationManager,
                    endpoints.getTokenServices(),
                    endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory())); //密码模式
        }

        list.add(new RefreshTokenGranter
                (endpoints.getTokenServices(),
                        endpoints.getClientDetailsService(),
                        endpoints.getOAuth2RequestFactory())); //刷新token模式

        list.add(new ResourceOwnerSmscodeTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //手机号验证码模式

        list.add(new AuthorizationCodeTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //授权码模式

        list.add(new ImplicitTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //简化模式


        list.add(new ResourceOwnerSocialcodeTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //社交模式

        //客户端模式
        list.add(new ClientCredentialsTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(list);
    }

    /* 自定义 state key*/
    @Bean
    public StateKeyGenerator stateKeyGenerator() {
        return new CustomStateKeyGenerator();
    }
}

