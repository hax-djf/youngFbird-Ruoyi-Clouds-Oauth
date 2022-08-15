package com.flybirds.oauth.manger.task.impl;

import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.sign.Md5Utils;
import com.flybirds.oauth.factory.LoginScopeFactory;
import com.flybirds.oauth.manger.scope.LoginScopeHandler0;
import com.flybirds.oauth.manger.task.LoginTaskAdapter0;
import com.flybirds.security.oauth.OauthRedisManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * login success task manger
 *
 * @author :flbirds
 */
public class LoginSuccessManger0 extends LoginTaskAdapter0 {

    private static final Logger log = LoggerFactory.getLogger(AccountLoginSuccessService0.class);


    @Override
    public void submitTask(AuthToken authToken, AccountLoginChannelEnum channel) {

        submitMechanismTask(authToken0 ->{
            return CacheConstantEnum.LOGIN_TOKENLIST_KEY
                    .buildSuffix(accessToeknListCachetKey(authToken));
        } ,authToken);
        log.info("[ {} ]任务提交完成在线用户制度规则,用户[ {} ]",channel.getLabel(),authToken.getUser_name());
        saveAccessToeknToRedis(authToken);
        log.info("[ {} ]任务提交完成 token 存储,用户[ {} ]",channel.getLabel(),authToken.getUser_name());
    }
    /**
     * @since 1.0
     * 用户登录成功 任务机制
     *
     * @param authToken
     */
    private void submitMechanismTask(Function<AuthToken,String> function, AuthToken authToken){

        String key = function.apply(authToken);
        //todo 此scope不是oauth中的Scope用户权限范围
        LoginScopeHandler0 service = LoginScopeFactory.get0(UserAgentUtils.getDevice(authToken.getUser_agent()));
        if(OauthRedisManger.builder().exists(key)){
            service.scopeDetect(key,authToken);
        }
        service.saveTokenList(key,authToken);
    }

    private void saveAccessToeknToRedis(AuthToken authToken) {
        OauthRedisManger.builder()
                .setObjectByKey(CacheConstantEnum.LOGIN_TOKEN_KEY.buildSuffix(accessToeknCacheKey(authToken)),
                authToken.getAccessToken(),Long.valueOf(authToken.getExpires_in()), TimeUnit.SECONDS);
    }
    private String accessToeknCacheKey(AuthToken authToken){
        String userMd5Token = Md5Utils.hash(new StringBuilder(authToken.getUser_name())
                .append(authToken.getOauth_token_secretkey())
                .toString());
        return userMd5Token;
    }
    private String  accessToeknListCachetKey(AuthToken authToken) {
        return Md5Utils.hash(new StringBuilder(authToken.getUser_name())
//                .append(authToken.getScope())
                .append(authToken.getUser_id())
                .toString());
    }


}
