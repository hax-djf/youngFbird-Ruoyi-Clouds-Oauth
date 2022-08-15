package com.flybirds.common.util.result;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.exception.ErrorCode;

import java.util.HashMap;

/**
 * 操作消息提醒
 * 
 * @author flybirds
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
        super();
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg) {
        super();
        this.put(CODE_TAG,code);
        this.put(MSG_TAG,msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super();
        this.put(CODE_TAG,code);
        this.put(MSG_TAG,msg);
        if (ObjectUtil.isNotNull(data))
        {
            this.put(DATA_TAG,data);
        }
    }

    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success(MsgConstant.SUCCESS);
    }

    /**
     * 返回成功数据
     * 
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success(MsgConstant.SUCCESS, data);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(CodeConstant.Number.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error(MsgConstant.FAILURE);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(CodeConstant.Number.FAILURE, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 适配 errorCode
     * @param errorCode
     * @return
     */
    public static  AjaxResult error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    /**
     * 是否成功
     */
    public boolean isSuccess(){
        return ObjectUtil.equal(CodeConstant.character.SUCCESS,String.valueOf(this.get(CODE_TAG)));
    }
}
