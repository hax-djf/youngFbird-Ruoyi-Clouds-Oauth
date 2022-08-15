package com.flybirds.oauth.manger.scope;

import com.flybirds.common.core.model.AuthToken;

/**
 *  用户登录 scope 检测
 *
 * @author :flybirds
 */
public interface LoginScopeHandler0 {

    /**
     * @since 1.0
     * 登录检测
     *
     * @param key
     * @param authToken
     */
    void scopeDetect(String key,AuthToken authToken);

    /**
     * @since 1.0
     * 存储tokenList -key
     *
     * @param key
     * @param authToken
     */
    void saveTokenList(String key,AuthToken authToken);

}
