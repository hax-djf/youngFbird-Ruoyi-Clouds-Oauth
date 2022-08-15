package com.flybirds.system.sysUser.controller;

import cn.hutool.core.lang.Validator;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.validator.ValidatorUtils;
import com.flybirds.system.sysUser.service.ISysUserService;
import com.flybirds.system.sysUser.service.UserValidateService;
import com.flybirds.system.sysUser.vo.RegisterAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.flybirds.common.util.result.Result.fail;
import static com.flybirds.common.util.result.Result.ok;

/**
 * @author :flybirds
 */
@RequestMapping("/register")
@RestController
@RequiredArgsConstructor
public class SysRegisterController {

    private final UserValidateService userValidateService;
    private final ISysUserService iSysUserService;


    /**
     * 发送邮箱验证码
     *
     * @return
     */
    @GetMapping("/email/code")
    public Result sendEmailCode(@RequestParam String email) {
        Validator.validateEmail(email, "邮箱地址不正确");
        userValidateService.sendEmailCode(email);
        return ok();
    }

    /**
     * 邮箱注册用户
     */
    @PostMapping("/email")
    public Result emailRegister(@RequestBody RegisterAccountRequest request) {
        Validator.validateEmail(request.getEmail(), "邮箱地址不正确");
        ValidatorUtils.validateEntity(request, RegisterAccountRequest.EmailGroup.class);
        return iSysUserService.emailRegister(request);
    }

    /**
     * 注册用户，发送手机验证码
     *
     * @return
     */
    @GetMapping("/phone/code")
    public Result sendPhoneCode(@RequestParam String phoneNumber) {
        Validator.validateMobile(phoneNumber, "手机号码不正确");
        userValidateService.sendPhoneCode(phoneNumber);
        return ok();
    }

    /**
     * 手机号注册
     *
     * @return
     */
    @PostMapping("/register/phone")
    public Result phoneRegister(@RequestBody RegisterAccountRequest request) {
        Validator.validateMobile(request.getPhoneNumber(), "手机号码不正确");
        ValidatorUtils.validateEntity(request, RegisterAccountRequest.PhoneNumberGroup.class);
        if (!userValidateService.checkPhoneCode(request.getPhoneNumber(), request.getCode(), CacheConstantEnum.REGISTER_MOBILE_CODE.getKey())) {
            return fail("验证码错误");
        }
        return iSysUserService.phoneRegister(request);
    }


    
}
