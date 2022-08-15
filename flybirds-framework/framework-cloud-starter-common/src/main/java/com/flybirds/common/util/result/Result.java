package com.flybirds.common.util.result;

import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.exception.ErrorCode;
import com.flybirds.common.exception.ServiceException;
import com.flybirds.common.exception.enums.GlobalErrorCodeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/***
 * http请求返回 [data] 格式
 *
 * @author flybirds
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 成功 */
    public static final Integer SUCCESS = CodeConstant.Number.SUCCESS;

    /* 失败 */
    public static final Integer FAIL = CodeConstant.Number.FAILURE;

    private Integer code = CodeConstant.Number.SUCCESS;

    private String msg = MsgConstant.SUCCESS;

    private T data;

    /* 成功的几种重载 */
    public static <T> Result<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> Result<T> ok(String msg) {
        return restResult(null, SUCCESS, msg);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    /* 失败的几种重载 */
    public static <T> Result<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> Result<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> Result<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        return restResult(null, code, msg);
    }

    /**
     * 适配code为string类型
     *
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(String code, String msg) {
        return restResult(null, Integer.valueOf(code), msg);
    }

    /**
     * 适配httpStatus枚举类型
     *
     * @param httpStatus
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(HttpStatus httpStatus, String msg) {
        return restResult(null, httpStatus.value(), msg);
    }

    /**
     * 适配 errorCode 类型
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMsg());
    }

    private static <T> Result<T> restResult(T data, Integer code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
    /** 判断业务是否正常 */
    public boolean onSuccess() {
        return this.code.equals(SUCCESS);
    }
    public static boolean onSuccess(Integer code) {
        return Objects.equals(code, SUCCESS);
    }
    /** 判断业务是否失败 */
    public boolean unSuccess() {
        return !onSuccess();
    }
    public static boolean unSuccess(Integer code) {return !onSuccess(code);}

    /* 判断是否有异常。如果有，则抛出 {@link ServiceException} 异常 */
    public void checkError() throws ServiceException {
        if (onSuccess()) {
            return;
        }
        // 业务异常
        throw new ServiceException(code, msg);
    }

    //抛异常
    public static void errorFail(String msg){
        //抛异常
        throw new BaseException(msg);
    }

    //get set
    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}