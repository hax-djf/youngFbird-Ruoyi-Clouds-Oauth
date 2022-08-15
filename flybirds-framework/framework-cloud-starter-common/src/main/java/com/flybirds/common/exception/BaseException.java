package com.flybirds.common.exception;


import cn.hutool.core.util.ObjectUtil;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.util.i18n.MessageUtils;
import com.flybirds.common.util.result.AjaxResult;

/**
 * 通用【BASE】 基础异常
 *
  @author ruoyi
 */
public class BaseException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code = CodeConstant.character.FAILURE;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(String module, String code, Object[] args, String defaultMessage)
    {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String module, String code, Object[] args)
    {
        this(module, code, args, null);
    }

    public BaseException(String code, Object[] args, String defaultMessage)
    {
        this(null, code, args, defaultMessage);
    }

    public BaseException(String module, String defaultMessage)
    {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args)
    {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage)
    {
        this(null, null, null, defaultMessage);
    }

    //支持AjaxResult
    public BaseException(AjaxResult ajaxResult)
    {
        this(null, null, null,String.valueOf(ajaxResult.get(AjaxResult.MSG_TAG)));
    }
    //支持ErrorCode
    public BaseException(ErrorCode errorCode)
    {
        this(null,
            String.valueOf(errorCode.getCode()),
            null,
            errorCode.getMsg());
    }

    @Override
    public String getMessage()
    {
        String message = null;
        if (!ObjectUtil.isNull(code))
        {
            message = MessageUtils.message(code, args);
        }
        if (message == null)
        {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule()
    {
        return module;
    }

    public String getCode()
    {
        return code;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public String getDefaultMessage()
    {
        return defaultMessage;
    }
}
