package com.flybirds.gateway.service;


import com.flybirds.common.exception.CaptchaException;
import com.flybirds.common.util.result.AjaxResult;

import java.io.IOException;

/**
 * 验证码服务接口
 *
 * @author ruoyi
 */
public interface VerificationCodeService {

    /*** 生成验证码*/
     AjaxResult createCapcha() throws IOException, CaptchaException;

    /*** 校验验证码*/
     void checkCapcha(String key, String value) throws CaptchaException;

}


