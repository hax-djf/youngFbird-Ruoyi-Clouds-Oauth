package com.flybirds.common.exception;


/**
 * 用户信息异常类
 * 
 * @author ruoyi
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args, String message)
    {
        super("user", code, args, message);
    }

    public UserException(String code, String message) {
        super("user", code, null, message);
    }
}
