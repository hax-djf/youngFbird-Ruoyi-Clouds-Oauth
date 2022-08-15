package com.flybirds.common.enums.login;

import com.flybirds.common.handler.DisplayedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author :flybirds
 */
@Getter
@AllArgsConstructor
public enum AccountLoginStatusEnum implements DisplayedEnum {


    ACCOUNT_LOGIN_SUCCESS(0,"账号登录成功","AccountLoginSuccessService0"),
    ACCOUNT_LOGIN_FAILURE(1,"账号登录失败","AccountLoginFailureService0"),
    SMS_LOGIN_SUCCESS(0,"短信登录成功","SmsLoginSuccessService0"),
    SMS_LOGIN_FAILURE(1,"短信登录失败","SmsLoginFailureService0"),
    LOGIN_SUCCESS(0,"登录成功","DefaultLoginSuccessService0"),
    LOGIN_FAILURE(1,"登录失败","DefaultLoginFailureService0"),
    ;
    private Integer value;

    private String label;

    private String className;
}
