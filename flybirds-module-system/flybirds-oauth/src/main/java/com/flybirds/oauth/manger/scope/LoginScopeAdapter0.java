package com.flybirds.oauth.manger.scope;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.security.oauth.OauthRedisManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  用户登录 scope 检测
 *
 * @author :flybirds
 */
public abstract class LoginScopeAdapter0 implements LoginScopeHandler0{

    private Logger log = LoggerFactory.getLogger(LoginScopeAdapter0.class);

    @Override
    public void saveTokenList(String key, AuthToken authToken){
        OauthRedisManger.builder().setCacheListLeftPush(key,authToken);
        log.info("用户 {} tokenList 存储成功, 时间: {}",authToken.getUser_name(), DateUtils.getTime());


    }
}
