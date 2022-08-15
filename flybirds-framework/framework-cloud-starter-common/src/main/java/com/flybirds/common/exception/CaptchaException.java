package com.flybirds.common.exception;

/**
 * 登录数字验证码错误异常类
 *
 * @author flybirds
 */
public class CaptchaException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException(String message)
    {
        super(message);
    }
}
