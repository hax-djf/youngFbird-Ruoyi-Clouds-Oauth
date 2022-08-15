package com.flybirds.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CacheConstantEnum 缓存redis key
 *
 * @author flybirds
 */
@Getter
@AllArgsConstructor
public enum CacheConstantEnum {

    /* {sysetm } */
    SYS_CONFIG_KEY("sys_config:","参数管理 cache key"),
    SYS_DICT_KEY("sys_dict:","字典管理 cache key"),

    /* 用户信息 */
    EMAIL_CODE("user:email:code:{}","邮箱验证码"),
    MOBILE_NUMBER_CODE("user:mobile:code:{}","手机号验证码"),
    REGISTER_MOBILE_CODE("user:register:mobile:code:","用户注册手机验证码"),
    MOBILE_RETRIEVE_PWD_CODE("user:mobile:retrieve:pwd:code:","手机找回密码验证码"),
    RETRIEVE_PWD_USER_CODE("user:retrieve:pwd:code:{}","找回密码验证完成用户身份code"),
    UPDATE_USER_EMAIL_CODE("user:email:update:code:{}:{}","修改邮箱验证码"),

    /* {oauth} */
    OAUTH_ACCESS("oauth:access:","oauth 缓存前缀 oauth2.0适配设置基于redis存储的key前缀"),
    OAUTH_ACCESS_ACCESS("oauth:access:access:","redistoken缓存前缀, 后面的access:是oauth2.0认证模式所携带的"),
    LOGIN_TOKEN_KEY("login:oauth:token:","登录用户前缀token key"),
    LOGIN_TOKENLIST_KEY("login:oauth:tokenList:","权限用户List"),
    CLIENT_DETAILS_KEY("oauth:client:details:","oauth 客户端信息"),

    CAPTCHA_CODE_KEY_MOBILE("login:mobile:code:","验证码【登录短信验证码】"),
    CAPTCHA_CODE_KEY_MOBILE_NUMBER("login:mobile:codeList:","验证码【登录短信验证码】次数"),


    CAPTCHA_CODE_KEY("captcha_codes:","验证码"),
    REPEAT_SUBMIT_KEY("repeat_submit:","防重提交 redis key");


    private String key;

    private String desc;

    /* 组合key + suffix */
    public String buildSuffix(Object target){
        return this.getKey() + String.valueOf(target);
    }

    /* 组合key + suffix */
    public String buildSuffix(String target){
        return this.getKey() + target;
    }

}
