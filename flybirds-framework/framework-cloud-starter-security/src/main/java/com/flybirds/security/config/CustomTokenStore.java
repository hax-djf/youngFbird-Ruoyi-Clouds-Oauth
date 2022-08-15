package com.flybirds.security.config;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.util.sign.Md5Utils;
import com.flybirds.security.oauth.OauthRedisManger;
import com.flybirds.security.service.UserTokenServiceManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 自定义CustomTokenStore适配 reids和mysql中存储
 *
 * @author :flybirds
 */
@Component
public class CustomTokenStore extends RedisTokenStore {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final long EXPIRE_TIME = 15 * 60;

    private static final long  REFRESH_EXPIRE_TIME = EXPIRE_TIME * 2 * 1000L;

    public Logger log = LoggerFactory.getLogger(CustomTokenStore.class);

    private JdbcTokenStore jdbcTokenStore;

    public CustomTokenStore(RedisConnectionFactory connectionFactory, DataSource dataSource) {
        super(connectionFactory);
        this.jdbcTokenStore =  new JdbcTokenStore(dataSource);
    }

    /**
     * 校验token的流程：
     * 1，在CheckTokenEndpoint.checkToken()校验token时，会先通过resourceServerTokenServices.readAccessToken(value)
     * 2，而resourceServerTokenServices的默认实现就是DefaultTokenServices
     * 3，resourceServerTokenServices.readAccessToken是通过tokenStore.readAccessToken(accessToken)实现的
     * 4，所以我们可以在此时刷新token
     * 在这里刷新的优点是：减少了网络交互，无需再从客户端发送/oauth/token?grant_type=refresh_token请求来刷新token
     * 同时，这种自动续签的方式不会重新生成token，如果想重新生成token可以参考DefaultTokenServices.createAccessToken()
     * @param tokenValue
     * @return
     */
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) super.readAccessToken(tokenValue);
        if (oAuth2AccessToken != null) {
            //秒级比较
            if (oAuth2AccessToken.getExpiresIn() < EXPIRE_TIME) {

                /**
                 * 重置时间 这里是半个小时，可以自定义在配置文件中或者通过clientid获取原定的过期时间
                 * todo 注意这个位置刷新了token的的时间，但是jwt中的额过期时间还没有续期时间
                 */
                oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE_TIME));
                //重置用户在想缓存数据
                refreshToken(REFRESH_EXPIRE_TIME/1000L, oAuth2AccessToken);
                OAuth2Authentication oAuth2Authentication = super.readAuthentication(tokenValue);
                super.storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
                if(isOauth2()){
                    jdbcTokenStore.storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
                }
            }
        }
        return oAuth2AccessToken;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        super.storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
        if(isOauth2()){
            jdbcTokenStore.storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
        }

    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        super.removeAccessToken(oAuth2AccessToken);
        if(isOauth2()){
            jdbcTokenStore.removeAccessToken(oAuth2AccessToken);
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication oAuth2Authentication) {
        super.storeRefreshToken(oAuth2RefreshToken, oAuth2Authentication);
        if(isOauth2()){
            jdbcTokenStore.storeRefreshToken(oAuth2RefreshToken, oAuth2Authentication);
        }
    }


    @Override
    public void removeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        super.removeRefreshToken(oAuth2RefreshToken);
        if(isOauth2()){
            jdbcTokenStore.removeRefreshToken(oAuth2RefreshToken);
        }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        super.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
        if(isOauth2()){
            jdbcTokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
        }
    }

    protected  void refreshToken(long EXPIRE_TIME,DefaultOAuth2AccessToken oAuth2AccessToken) {
        /**
         * 需要获取到oauth2Access数据
         * 1.在线usertoken,在线用户
         * 2.获取到2个key值，进行时间设置
         */
        AuthToken authToken = UserTokenServiceManger.oAuth2AccessTokenToAuthToken(oAuth2AccessToken);
        try {

            OauthRedisManger.builder().expireTimeUserTokenBoth2Keys(getUserTokenToCacheKey(authToken)
                    ,getUserTokenListToCachetKey(authToken),EXPIRE_TIME);

            log.info("刷新在线用户token数据成功 用户名{}",authToken.getUser_name());

        }catch (RuntimeException e){
            e.printStackTrace();
            throw new BaseException(String.format("刷新在线用户token数据失败 用户名 %s",authToken.getUser_name()));
        }
    }

    protected String getUserTokenToCacheKey(AuthToken authToken) {
        return Md5Utils.hash(new StringBuilder(authToken.getUser_name())
                .append(authToken.getOauth_token_secretkey())
                .toString());
    }

    protected String  getUserTokenListToCachetKey(AuthToken authToken) {

        return Md5Utils.hash(new StringBuilder(authToken.getUser_name())
                .append(authToken.getScope()).append(authToken.getUser_id())
                .toString());
    }

    protected Boolean isOauth2(){
        return ObjectUtil.equal(applicationName, Constant.CloudServiceName.FLYBIRDS_OAUTH);
    }

    public JdbcTokenStore getJdbcTokenStore() {
        return jdbcTokenStore;
    }

    public void setJdbcTokenStore(JdbcTokenStore jdbcTokenStore) {
        this.jdbcTokenStore = jdbcTokenStore;
    }
}
