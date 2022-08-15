package com.flybirds.common.enums.login;

import com.flybirds.common.handler.DisplayedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * login channel
 *
 * @author :flybirds
 */
@AllArgsConstructor
@Getter
public enum AccountLoginChannelEnum implements DisplayedEnum ,Serializable {

    SMS(0,"短信登录(验证码)","SmsLoginServiceImpl0",AccountLoginGrantType.OAUTH2_SMS_CONSTANT),
    APP_CODE(1,"app (扫码)","AccountLoginServiceImpl0",AccountLoginGrantType.OAUTH2_TOKEN_CONSTANT),
    ACCOUNT_EMAIL_MOBILE(2,"账号/邮件/手机(密码模式)","AccountLoginServiceImpl0",AccountLoginGrantType.OAUTH2_PASSWORD_CONSTANT),
    SOCIAL(3,"社交登录","SocialLoginServiceImpl0",AccountLoginGrantType.OAUTH2_SOCIAL_CONSTANT),
    SOCIAL2(4,"社交登录&账号密码","SocialLoginServiceImpl2",AccountLoginGrantType.OAUTH2_PASSWORD_CONSTANT);

    private Integer value;

    private String label;

    /* 实现类0 */
    private String className;

    /* 授权类型 */
    private String grantType;

    public static AccountLoginChannelEnum get(Integer value){
        AccountLoginChannelEnum channel = null;
        for(AccountLoginChannelEnum channelEnum : AccountLoginChannelEnum.values()){
            if(value == channelEnum.getValue()){
                channel=  channelEnum;
                break;
            }
        }
        return channel;
    }

    public static final String ACCOUNT_LOGIN_CHANNEL = "account_login_channel";

}
