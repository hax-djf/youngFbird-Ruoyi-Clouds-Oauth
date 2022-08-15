package com.flybirds.smsprovider.client.core;

import com.flybirds.common.exception.ErrorCode;
import com.flybirds.smsprovider.client.enums.SmsFrameworkErrorCodeConstants;

import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsResult
 * @see SmsFrameworkErrorCodeConstants
 *
 * @author 芋道源码
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {

}
