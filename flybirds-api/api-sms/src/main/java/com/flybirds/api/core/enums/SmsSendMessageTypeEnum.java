package com.flybirds.api.core.enums;

import com.flybirds.common.constant.CacheConstantEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author :flybirds
 */
@Getter
@AllArgsConstructor
public enum SmsSendMessageTypeEnum {

    //登录
    LOGIN_SYSTEM_MOBILE("阿里云",1482275954393919492l,"SMS_186968097", CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE.getKey()),
    //注册
    REGISTER_SYSTEM_MOBILE("阿里云",1482275954393919492l,"SMS_186968097", CacheConstantEnum.MOBILE_RETRIEVE_PWD_CODE.getKey()),
    //找回密码
    RETRIEVE_PWD_SYSTEM_CODE("阿里云",1482275954393919492l,"SMS_186968097",CacheConstantEnum.REGISTER_MOBILE_CODE.getKey()),
    //手机短信验证码
    UPDATE_PHONE_NUMBER_CODE("阿里云",1482275954393919492l,"SMS_186968097",CacheConstantEnum.MOBILE_NUMBER_CODE.getKey())
    ;

    //渠道名称
    private String channelName;
    //渠道id
    private Long channelId;
    //模板名称id
    private String apiTemplateId;
    //短信存储key值
    private String smsKeyPrefix;
}
