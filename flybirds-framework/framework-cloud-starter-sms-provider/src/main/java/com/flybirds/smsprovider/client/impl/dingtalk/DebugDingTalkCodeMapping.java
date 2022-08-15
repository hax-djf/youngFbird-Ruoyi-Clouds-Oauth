package com.flybirds.smsprovider.client.impl.dingtalk;


import com.flybirds.common.exception.ErrorCode;
import com.flybirds.common.exception.enums.GlobalErrorCodeConstants;
import com.flybirds.smsprovider.client.core.SmsCodeMapping;
import com.flybirds.smsprovider.client.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author 芋道源码
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "200") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
