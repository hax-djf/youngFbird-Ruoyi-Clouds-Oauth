package com.flybirds.system.sysUser.service;


import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.util.result.Result;
import com.flybirds.system.sysUser.vo.RetrievePasswordRequest;
import com.flybirds.system.sysUser.vo.UpdateUserRequest;

import javax.validation.constraints.NotBlank;

/**
 * 账号验证码
 *
 * @author : smalljop
 * @description :
 * @create : 2020-12-15 15:45
 **/
public interface UserValidateService {

    /**
     * 发送邮箱验证码
     *
     * @param email
     */
    void sendEmailCode(String email);


    /**
     * 发送重置密码邮件
     *
     * @param email
     * @param userEntity
     */
    void sendResetPwdEmail(String email, SysUser userEntity);

    /**
     * 发送短信验证码
     *
     * @param phoneNumber
     */
    void sendPhoneCode(String phoneNumber);


    /**
     * 验证码是否正确
     *
     * @param phoneNumber
     */
    Boolean checkPhoneCode(String phoneNumber, String code, String cacheKey);


    /**
     * 找回密码验证码
     *
     * @param phoneNumber
     */
    void sendRetrievePwdPhoneCode(String phoneNumber);


    /**
     * 重置密码身份编码 验证通过之后生成
     *
     * @param userId
     * @return code
     */
    String getRestPasswordCode(Long userId);

    /**
     * 找回
     * @param phoneNumber
     * @param code
     * @return
     */
    boolean checkPhoneBackCode(@NotBlank(message = "手机号不能为空") String phoneNumber, @NotBlank(message = "验证码不能为空") String code);

    /**
     * 根据返回的code 找回密码
     * @param request
     * @return
     */
    Result backPasswordReset(RetrievePasswordRequest.Reset request);

    /**
     * 发送绑定账号邮箱
     * @param email
     * @param userId
     */
    void sendUpdateAccountEmail(String email, Long userId);

    /**
     * 获取需要更改邮箱的UserId
     *
     * @param request
     */
    Long getUpdateEmailUserId(UpdateUserRequest.Email request);

    /**
     * 发送短信验证码-修改绑定手机号
     * @param phoneNumber
     */
    void sendUpdatePhoneCode(String phoneNumber);
}
