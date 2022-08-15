package com.flybirds.system.sysUser.service.Impl;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.flybirds.api.RemoteSmsService;
import com.flybirds.api.core.dto.SysSmsSendMessageDto;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.util.result.Result;
import com.flybirds.email.service.MailService;
import com.flybirds.redis.manger.RedisCacheManger;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.system.sysUser.service.ISysUserService;
import com.flybirds.system.sysUser.service.UserValidateService;
import com.flybirds.system.sysUser.vo.RetrievePasswordRequest;
import com.flybirds.system.sysUser.vo.UpdateUserRequest;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.flybirds.api.core.enums.SmsSendMessageTypeEnum.REGISTER_SYSTEM_MOBILE;
import static com.flybirds.api.core.enums.SmsSendMessageTypeEnum.RETRIEVE_PWD_SYSTEM_CODE;
import static com.flybirds.api.core.enums.SmsSendMessageTypeEnum.UPDATE_PHONE_NUMBER_CODE;

/**
 * 验证码校验等服务
 *
 * @author : flbirds-hax
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserValidateServiceImpl implements UserValidateService {

    private final static String REG_EMAIL_TITLE = "YOUNG-FBIRD 数字化平台-注册验证码";

    private final static String RESET_PWD_TITLE = "重置密码";

    private final static String RESET_EMAIL_TITLE = "重置邮箱";

    private final RedisCacheManger redisUtils;

    private final MailService mailService;

    private final RemoteSmsService remoteSmsService;

    private final ISysUserService userService;

    /**
     * 重置密码地址
     */
    @Value("${flybirds.platform.front.resetPwdUrl}")
    private String resetPwdUrl;

    @Value("${flybirds.platform.front.updateEmailUrl}")
    private String updateEmailUrl;

    @Override
    public void sendEmailCode(String email) {
        String code = genValidateCode(StrUtil.format(CacheConstantEnum.EMAIL_CODE.getKey(), email));
        //发送邮件
        mailService.sendTemplateHtmlMail(email, REG_EMAIL_TITLE, "mail/reg-code", MapUtil.of("code", code));
    }

    /**
     * 发送重置密码邮件
     * @param email
     * @param userEntity
     */
    @Override
    public void sendResetPwdEmail(String email, SysUser userEntity) {
        String code = getRestPasswordCode(userEntity.getUserId());
        //发送邮件
        Map<String, Object> params = ImmutableMap.of("email", email, "resetPwdUrl", StrUtil.format(resetPwdUrl, code, email));
        mailService.sendTemplateHtmlMail(email, RESET_PWD_TITLE, "mail/reset-password", params);
    }

    /**
     * 生成验证码
     */
    private String genValidateCode(String key) {
        //生成验证码
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", Constant.ConstantNumber.FOUR);
        String code = randomGenerator.generate();
        redisUtils.setCacheObject(key, code, 5L, TimeUnit.MINUTES);
        log.debug("genValidateCode:{}", code);
        return code;
    }

    @Override
    public void sendPhoneCode(String phoneNumber) {

        remoteSmsService.sendSmsMessage(new SysSmsSendMessageDto().buildSmsMessage(
                phoneNumber,REGISTER_SYSTEM_MOBILE
        ));
    }

    /**
     * 校验短信验证码
     * @param phoneNumber
     * @param code
     * @return
     */
    @Override
    public Boolean checkPhoneCode(String phoneNumber, String code,String cacheKey) {
        String phoneCodeKey = cacheKey + phoneNumber;
        String validateCode = redisUtils.getCacheObject(phoneCodeKey, String.class);
        if (ObjectUtil.equal(code,validateCode)) {
            redisUtils.deleteObject(phoneCodeKey);
            return true;
        }
        return false;

    }

    @Override
    public void sendRetrievePwdPhoneCode(String phoneNumber) {
        remoteSmsService.sendSmsMessage(new SysSmsSendMessageDto().buildSmsMessage(phoneNumber,
                RETRIEVE_PWD_SYSTEM_CODE));
    }

    @Override
    public void sendUpdatePhoneCode(String phoneNumber) {
        remoteSmsService.sendSmsMessage(new SysSmsSendMessageDto().buildSmsMessage(phoneNumber,
                UPDATE_PHONE_NUMBER_CODE));
    }

    @Override
    public String getRestPasswordCode(Long userId) {
        String code = IdUtil.fastUUID();
        redisUtils.setCacheObject(StrUtil.format(CacheConstantEnum.RETRIEVE_PWD_USER_CODE.getKey(), code), userId);
        return code;
    }

    @Override
    public boolean checkPhoneBackCode(@NotBlank(message = "手机号不能为空") String phoneNumber, @NotBlank(message = "验证码不能为空") String code) {
        String phoneBackCode =  CacheConstantEnum.MOBILE_RETRIEVE_PWD_CODE.getKey()  + phoneNumber;
        String cacheKey = redisUtils.getCacheObject(phoneBackCode);
        return  ObjectUtil.equal(code,cacheKey);
    }

    @Override
    public Result backPasswordReset(RetrievePasswordRequest.Reset request) {
        Result<Object> result = Result.ok("重置密码成功");
        String codeKey = StrUtil.format(CacheConstantEnum.RETRIEVE_PWD_USER_CODE.getKey(), request.getCode());
        Long userId = redisUtils.getCacheObject(codeKey, Long.class);
        if (ObjectUtil.isNull(userId)) {
            return Result.fail("重置密码操作已过期，请重试");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            return Result.fail("两次密码不一致");
        }

        if(userService.resetUserPwdById(userId, SecurityUtils.encryptPassword(request.getPassword())) > 0){
            redisUtils.deleteObject(codeKey);
        }else{
            result.errorFail("重置密码失败");
        }
        return result;
    }

    //异步执行发送
    @Override
    @Async("asyncTaskExecutor")
    public void sendUpdateAccountEmail(String email, Long userId) {
        String code = IdUtil.fastUUID();
        redisUtils.setCacheObject(StrUtil.format(CacheConstantEnum.UPDATE_USER_EMAIL_CODE.getKey(), code, email), userId);
        //发送邮件
        Map<String, Object> params = ImmutableMap.of("updateEmailUrl", StrUtil.format(updateEmailUrl, code, email));
        mailService.sendTemplateHtmlMail(email, RESET_EMAIL_TITLE, "mail/update-account-email", params);
    }

    @Override
    public Long getUpdateEmailUserId(UpdateUserRequest.Email request) {
        String emailCodeKey = StrUtil.format(CacheConstantEnum.UPDATE_USER_EMAIL_CODE.getKey(), request.getKey(), request.getEmail());
        Long userId = redisUtils.getCacheObject(emailCodeKey, Long.class);
        if (ObjectUtil.isNotNull(userId)) {
            redisUtils.deleteObject(emailCodeKey);
            return userId;
        }
        return null;
    }
}
