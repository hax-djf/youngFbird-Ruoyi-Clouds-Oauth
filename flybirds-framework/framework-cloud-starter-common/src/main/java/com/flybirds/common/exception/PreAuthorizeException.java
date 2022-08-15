package com.flybirds.common.exception;

/**
 * 权限异常
 *
 * @author flybirds
 */
public class PreAuthorizeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public PreAuthorizeException()
    {
        super("用户权限异常");
    }
}
