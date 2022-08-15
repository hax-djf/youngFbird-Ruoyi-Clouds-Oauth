package com.flybirds.common.util.assertions;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.exception.ErrorCode;
import com.flybirds.common.exception.util.ServiceExceptionUtil;

import java.util.Collection;
import java.util.function.Consumer;


/**
 * AssertUtil 断言工具类
 *
 * @author flybirds
 */
public class AssertUtil {

    /*判断字符串非空*/
    public static void isNotNull(String str, String... message){
        if (StrUtil.isBlank(str)) {
            execute(message);
        }
    }

    /*判断对象非空*/
    public static void isNotNull(Object obj, String... message){
        if (ObjectUtil.isNull(obj)) {
            execute(message);
        }
    }

    /*判断字符串非空*/
    public static void isNotNull(String str,RuntimeException e){
        if (StrUtil.isBlank(str)) {
            execute(e);
        }
    }

    /*判断对象非空*/
    public static void isNotNull(Object obj,RuntimeException e){
        if (ObjectUtil.isNull(obj)) {
            execute(e);
        }
    }


    /*判断字符串非空*/
    public static void isNotNull(String str, ErrorCode errorCode){
        if (StrUtil.isBlank(str)) {
            execute(errorCode);
        }
    }

    /*判断对象非空*/
    public static void isNotNull(Object obj, ErrorCode errorCode){
        if (ObjectUtil.isNull(obj)) {
            execute(errorCode);
        }
    }

    /* 判断数组不为空 */
    public static void isNotEmpty(Collection collection, String... message){
        if (ObjectUtil.isEmpty(collection)) {
            execute(message);
        }
    }

    /* 判断数组不为空 */
    public static void isNotEmpty(Collection collection, ErrorCode errorCode){
        if (ObjectUtil.isEmpty(collection)) {
            execute(errorCode);
        }
    }

    /* 判断数组不为空 */
    public static void isNotEmpty(Collection collection,RuntimeException e){
        if (ObjectUtil.isEmpty(collection)) {
            execute(e);
        }
    }

    /*判断结果是否为真*/
    public static void isTrue(Boolean isTrue, String... message){
        if (isTrue) {
            execute(message);
        }
    }

    /*判断结果是否为真*/
    public static void isTrue(Boolean isTrue, Consumer consumer, Object object, RuntimeException e){
        if (isTrue) {
            consumer.accept(object);
            execute(e);
        }
    }
    /*判断结果是否为真*/
    public static void isTrue(Boolean isTrue, Consumer consumer, Object object, String... message){
        if (isTrue) {
            consumer.accept(object);
            execute(message);
        }
    }

    /*判断结果是否为真*/
    public static void isTrue(Boolean isTrue, Consumer consumer, Object object, ErrorCode errorCod){
        if (isTrue) {
            consumer.accept(object);
            execute(errorCod);
        }
    }

    /*判断结果是否为真*/
    public static void isTrue(Boolean isTrue, ErrorCode errorCode){
        if (isTrue) {
            execute(errorCode);
        }
    }

    /*判断结果是否为真*/
    public static void isTrue(Boolean isTrue, RuntimeException e){
        if (isTrue) {
            execute(e);
        }
    }

    /*最终执行方法*/
    private static void execute(String... message){

        String msg = MsgConstant.ERROR_MESSAGE;
        if (message != null && message.length > 0) {
            msg = message[0];
        }
        throw new BaseException(msg);

    }

    /*最终执行方法*/
    private static void execute(RuntimeException e){
        throw e;
    }

    /*最终执行方法*/
    private static void execute(ErrorCode errorCode){
        throw ServiceExceptionUtil.exception(errorCode);
    }

}
