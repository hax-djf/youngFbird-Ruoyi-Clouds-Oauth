package com.flybirds.system.sysUser.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.validator.ValidatorUtils;
import com.flybirds.system.sysUser.service.ISysUserService;
import com.flybirds.system.sysUser.service.UserValidateService;
import com.flybirds.system.sysUser.vo.RetrievePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.flybirds.common.util.result.Result.fail;
import static com.flybirds.common.util.result.Result.ok;


/**
 * 用户找回密码服务
 *
 * @author :flybirds
 */
@RequestMapping("/retrieve")
@RestController
@RequiredArgsConstructor
public class SysRetrieveController {

    private final UserValidateService userValidateService;

    private final ISysUserService iSysUserService;
    /**
     * 找回密码手机发送验证码
     */
    @GetMapping("/password/phone/code")
    public Result sendRetrievePwdPhoneCode(@RequestParam String phoneNumber) {
        Validator.validateMobile(phoneNumber, "手机号码不正确");
        SysUser sysUser = iSysUserService.selectUserByPhone(phoneNumber);
        if (ObjectUtil.isNull(sysUser)) {
            return fail("该手机号尚未注册");
        }
        userValidateService.sendRetrievePwdPhoneCode(phoneNumber);
        return ok();
    }

    /**
     * 发送找回密码邮件 -> 邮箱密码找回主要是跳转前端的方式来实现的
     */
    @GetMapping("/password/email")
    public Result sendRetrievePwdEmail(@RequestParam String email) {
        Validator.validateEmail(email, "邮箱地址不正确");
        SysUser userEntity = iSysUserService.selectUserByUserEmail(email);
        if (ObjectUtil.isNull(userEntity)) {
            return fail("该邮箱尚未注册");
        }
        userValidateService.sendResetPwdEmail(email, userEntity);
        return ok();
    }

    /**
     * 检查找回密码手机验证码是否正确
     * 正确则返回身份Code
     *
     * @return
     */
    @PostMapping("/password/check/phone-code")
    public Result checkRetrievePwdPhoneCode(@RequestBody RetrievePasswordRequest.CheckPhoneCode request) {
        Validator.validateMobile(request.getPhoneNumber(), "手机号码不正确");
        ValidatorUtils.validateEntity(request);
        //校验验证码
        if (!userValidateService.checkPhoneBackCode(request.getPhoneNumber(),request.getCode())) {
            return fail("验证码错误");
        }
        SysUser userEntity = iSysUserService.selectUserByPhone(request.getPhoneNumber());
        if (ObjectUtil.isNotNull(userEntity)) {
            String restPasswordCode = userValidateService.getRestPasswordCode(userEntity.getUserId());
            return ok(restPasswordCode);
        }
        return ok();
    }

    /**
     * 找回重设密码
     *
     * @param request
     * @return
     */
    @PostMapping("/password/reset")
    public Result retrieveResetPasswordByPhone(@RequestBody RetrievePasswordRequest.Reset request) {
        ValidatorUtils.validateEntity(request);
        return  userValidateService.backPasswordReset(request);
    }


}
