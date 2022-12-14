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
 *  ??????????????????
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

    //???????????????
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
     * ????????????????????????????????????ClientDetailsService???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *  clientId???????????????????????????????????????Id???
     *  secret??????????????????????????????????????????????????????????????????????????????
     *  scope????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *  authorizedGrantTypes????????????????????????????????????????????????????????????
     *  authorities?????????????????????????????????????????????Spring Security authorities??????
     * @param clients {@link ClientDetailsServiceConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }


    /**
     * ???????????????????????? ClientDetails????????????????????????redis???????????????
     *
     * @return {@link RedisClientDetailsService}
     */
    public RedisClientDetailsService clientDetailsService() {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        return clientDetailsService;
    }

    /***
     * ?????????????????????token?????????????????????????????????(tokenservices)
     * AuthorizationServerEndpointsConfigurer ????????????????????????????????? pathMapping() ???????????????????????????URL??????????????????????????????
     *           ??????????????????String ????????????????????????URL??????????????????
     *           ??????????????????String ?????????????????????????????????URL?????????
     *            /oauth/authorize??????????????????
     *            /oauth/token??????????????????
     *            /oauth/confirm_access????????????????????????????????????
     *            /oauth/error????????????????????????????????????
     *            /oauth/check_token???????????????????????????????????????????????????
     *            /oauth/token_key????????????????????????????????????????????????JWT???????????????
     * @param endpoints {@link AuthorizationServerEndpointsConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .pathMapping("/oauth/confirm_access", "/oauth/confirmAccess") //??????????????????
                .userDetailsService(userDetailsService) // ????????????????????????
                .authenticationManager(authenticationManager) // ?????????????????????
                .authorizationCodeServices(authorizationCodeServices) //???????????????????????????
                .approvalStore(approvalStore) //????????????????????????
                .reuseRefreshTokens(false) // ?????????????????? refresh_token
                .exceptionTranslator(customWebResponseExceptionTranslator) // ?????????????????????
                .tokenServices(tokenService()); //??????tokenService
        endpoints.tokenGranter(tokenGranter(endpoints)); //?????????????????????
    }

    /***
     * * ???????????????????????????????????????
     *  ???1?????????????????????
     *  ???2????????????????????????
     *  ???3???????????????????????????????????????????????????????????? checkToken ??????????????? token ?????????
     *   tokenkey??????endpoint?????????JwtToken??????????????????????????????????????????????????????????????????????????????????????????endpoint???????????????
     * @param oauthServer {@link AuthorizationServerSecurityConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

        oauthServer.allowFormAuthenticationForClients() //??????????????????
                .passwordEncoder(passwordEncoder) //?????????????????????
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }


    /**
     * ????????????jwt??????????????? {@link } ??????????????????RS256 ???????????????
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        /**
         * JWT????????????????????????
         * Header ??????????????????HMAC SHA256???RSA???
         * Payload ??????????????????????????????????????????json????????????????????????????????????????????????????????????jwt????????????????????????
         * ?????????iss???????????????,exp?????????????????????, sub????????????????????????
         * Signature  ?????????????????????
         */
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyProperties.getKeyStore().getLocation(),                          //???????????? flybirds.jks
                keyProperties.getKeyStore().getSecret().toCharArray())              //???????????? flybirds
                .getKeyPair(
                        keyProperties.getKeyStore().getAlias(),                     //???????????? flybirds
                        keyProperties.getKeyStore().getPassword().toCharArray());   //???????????? flybirds
        converter.setKeyPair(keyPair);
        /**
         * {@link JwtAccessTokenConverter} ????????????
         * DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter()
         * accessTokenConverter.setUserTokenConverter();?????????????????????jwt?????????????????????
         */
        return converter;
    }

    /**
     * {@link AuthorizationServerTokenServices} tokenService ??? {@link AuthorizationServerEndpointsConfigurer} ???????????????token???
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices(); //????????????tokenSerice
        service.setSupportRefreshToken(true); //??????????????????
        service.setTokenStore(tokenStore); //??????tokenStore???????????????
        service.setClientDetailsService(clientDetailsService); //??????????????? ????????????????????????clientDetailsService ???????????????????????????????????????????????????????????????????????????????????????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); // ????????????????????????
        /** ??? {@link TokenEnhancer} ????????? {@link JwtAccessTokenConverter} ???????????????????????? */
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain); //?????????tokenService?????????
        return service;
    }

    /**
     * ?????????????????????????????????code????????????
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new CustomJdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * ????????? ???????????????
     */
    @Bean
    public ApprovalStore approvalStore(){
        return new CustomApprovalStore(dataSource);
    }

    /**
     * ?????????????????? KeyPair ???????????????
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("flybirds.jks"), "flybirds".toCharArray());
        return keyStoreKeyFactory.getKeyPair("flybirds", "flybirds".toCharArray());
    }

    /**
     * ?????????????????????
     */
    @Bean(name = "keyProp")
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }

    /**
     * {@link TokenEnhancer ??????????????? }
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (authentication.getUserAuthentication() != null) {
                Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
                LoginUser loginUser = (LoginUser) authentication.getUserAuthentication().getPrincipal();

                additionalInformation.put(SecurityConstant.DETAILS_USER_ID, loginUser.getUserId());
                additionalInformation.put(SecurityConstant.DETAILS_USERNAME, loginUser.getUsername());
                additionalInformation.put(SecurityConstant.DETAILS_MOBILE, loginUser.getSysUser().getPhonenumber()); //?????????
                additionalInformation.put(SecurityConstant.DETAILS_ONLINE_NUMBER, loginUser.getSysUser().getOnlineNumber()); //?????????????????????
                additionalInformation.put(SecurityConstant.DETAILS_TENANT_ID, loginUser.getSysUser().getTenantId()); //??????id
                additionalInformation.put(SecurityConstant.OAUTH_USER_AGENT, loginUser.getUserAgent());
                additionalInformation.put(SecurityConstant.OAUTH_USER_IP, loginUser.getUserIp());
                additionalInformation.put(SecurityConstant.OAUTH_TOKEN_SECRETKEY, userTokenService.getRandomSecretKey()); //??????????????????

                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
            }
            return accessToken;
        };
    }

    /**
     * Grant_type???????????????????????????  {@link AuthorizationServerEndpointsConfigurer}  {@link TokenGranter}
     */
    public TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> list = new ArrayList<>();
        if (authenticationManager != null) {
            list.add(new ResourceOwnerPasswordTokenGranter(authenticationManager,
                    endpoints.getTokenServices(),
                    endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory())); //????????????
        }

        list.add(new RefreshTokenGranter
                (endpoints.getTokenServices(),
                        endpoints.getClientDetailsService(),
                        endpoints.getOAuth2RequestFactory())); //??????token??????

        list.add(new ResourceOwnerSmscodeTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //????????????????????????

        list.add(new AuthorizationCodeTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //???????????????

        list.add(new ImplicitTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //????????????


        list.add(new ResourceOwnerSocialcodeTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory())); //????????????

        //???????????????
        list.add(new ClientCredentialsTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(list);
    }

    /* ????????? state key*/
    @Bean
    public StateKeyGenerator stateKeyGenerator() {
        return new CustomStateKeyGenerator();
    }
}

