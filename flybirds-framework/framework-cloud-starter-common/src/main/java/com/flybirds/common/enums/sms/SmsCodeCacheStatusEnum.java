package com.flybirds.common.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信发送存在在redis缓存的状态
 *
 * @author :flybirds
 */
@Getter
@AllArgsConstructor
public enum SmsCodeCacheStatusEnum {

    /**
     *正常
     */
    NORMAL("NORMAL","正常"),
    /**
     *未失效
     */
    NO_FAILURE("NO_FAILURE","未失效"),
    /**
     *频繁操作
     */
    FREQUENT_OPERATION("FREQUENT_OPERATION","频繁操作");

    private String value;

    private String label;
}
