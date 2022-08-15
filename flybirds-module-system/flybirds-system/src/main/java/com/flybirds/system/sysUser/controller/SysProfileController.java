package com.flybirds.system.sysUser.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.flybirds.api.RemoteFileService;
import com.flybirds.api.core.entity.SysFile;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.common.util.validator.ValidatorUtils;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.security.model.LoginUser;
import com.flybirds.security.service.UserTokenServiceManger;
import com.flybirds.system.sysUser.service.ISysUserService;
import com.flybirds.system.sysUser.service.UserValidateService;
import com.flybirds.system.sysUser.vo.UpdateUserRequest;
import com.flybirds.web.web.annotation.Login;
import com.flybirds.web.web.core.util.user.UserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 个人信息 业务处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/user/profile")
@SuppressWarnings("all")
public class SysProfileController extends MpBaseController {

    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private RemoteFileService remoteFileService;

    @Autowired
    private UserTokenServiceManger userTokenService;

    @Autowired
    private  UserValidateService userValidateService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile()
    {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserDetailById(userId);
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(username));
        ajax.put("postGroup", userService.selectUserPostGroup(username));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user)
    {
        if (userService.updateUserProfile(user) > 0)
        {
            LoginUser loginUser = userTokenService.getLoginUserToCache();
            // 更新缓存用户信息
            loginUser.getSysUser().setNickName(user.getNickName());
            loginUser.getSysUser().setPhonenumber(user.getPhonenumber());
            loginUser.getSysUser().setEmail(user.getEmail());
            loginUser.getSysUser().setSex(user.getSex());
            userTokenService.setLoginUserToCache(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword)
    {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        String password = user.getPassWord();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwdByName(username, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
            // 更新缓存用户密码
            LoginUser loginUser = userTokenService.getLoginUserToCache();
            loginUser.getSysUser().setPassWord(SecurityUtils.encryptPassword(newPassword));
            userTokenService.setLoginUserToCache(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }
    
    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            LoginUser loginUser = userTokenService.getLoginUserToCache();
            Result<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData()))
            {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            String url = fileResult.getData().getUrl();
            if (userService.updateUserAvatar(loginUser.getUsername(), url))
            {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", url);
                // 更新缓存用户头像
                loginUser.getSysUser().setAvatar(url);
                userTokenService.setLoginUserToCache(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }


    /**
     * 发送绑定邮箱邮件
     * @return
     */
    @GetMapping("/updateEmail/msg")
    @Log(title = "(个人信息) 发送绑定邮箱邮件", businessType = BusinessType.UPDATE)
    @Login
    public Result sendUpdateEmailMsg(@RequestParam String email) {
        Validator.validateEmail(email, "邮箱地址不正确");
        SysUser sysUser = userService.selectUserByUserEmail(email);
        if (ObjectUtil.isNotNull(sysUser)) {
            return Result.fail("该邮箱已被绑定");
        }
        userValidateService.sendUpdateAccountEmail(email, UserIdUtil.get());
        return Result.ok(true);
    }

    /**
     * 修改邮箱
     *
     * @return
     */
    @Log(title = "个人信息（修改邮箱）", businessType = BusinessType.UPDATE)
    @PostMapping("/update/email")
    public Result updateUserEmail(@RequestBody UpdateUserRequest.Email request) {
        Validator.validateEmail(request.getEmail(), "邮箱地址不正确");
        SysUser sysUser = userService.selectUserByUserEmail(request.getEmail());
        if (ObjectUtil.isNotNull(sysUser)) {
            return Result.fail("该邮箱已被绑定");
        }
        Long userId = userValidateService.getUpdateEmailUserId(request);
        if (ObjectUtil.isNull(userId)) {
            return Result.fail(false);
        }
        sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setEmail(request.getEmail());
        if(userService.updateById(sysUser)){
            LoginUser loginUser = userTokenService.getLoginUserToCache();
            // 更新缓存用户信息
            loginUser.getSysUser().setEmail(sysUser.getEmail());
            userTokenService.setLoginUserToCache(loginUser);
        }
        return Result.ok(true);
    }

    /**
     * 修改手机号验证码
     *
     * @return
     */
    @Log(title = "个人信息（修改手机号发送验证吗）", businessType = BusinessType.UPDATE)
    @GetMapping("/updatePhone/code")
    public Result sendUpdatePhoneCode(@RequestParam String phoneNumber) {
        Validator.validateMobile(phoneNumber, "手机号码不正确");
        SysUser userEntity = userService.selectUserByPhone(phoneNumber);
        if (ObjectUtil.isNotNull(userEntity)) {
            return Result.fail("该手机号已被绑定");
        }
        userValidateService.sendUpdatePhoneCode(phoneNumber);
        return Result.ok();
    }

    /**
     * 修改手机号
     *
     * @return
     */
    @Login
    @Log(title = "个人信息（修改手机号）", businessType = BusinessType.UPDATE)
    @PostMapping("/update/phoneNumber")
    public Result updatePhoneNumber(@RequestBody UpdateUserRequest.PhoneNumber request, @RequestAttribute Long userId) {
        ValidatorUtils.validateEntity(request);
        Validator.validateMobile(request.getPhoneNumber(), "手机号码不正确");
        //检查验证码是否正确
        Boolean checkPhoneCode = userValidateService.checkPhoneCode(request.getPhoneNumber(), request.getCode(), CacheConstantEnum.MOBILE_NUMBER_CODE.getKey());
        if (checkPhoneCode) {
            return Result.fail("验证码错误");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setPhonenumber(request.getPhoneNumber());
        if(userService.updateById(sysUser)){
            LoginUser loginUser = userTokenService.getLoginUserToCache();
            // 更新缓存用户信息
            loginUser.getSysUser().setEmail(sysUser.getPhonenumber());
            userTokenService.setLoginUserToCache(loginUser);
        }
        return Result.ok();
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息(用户同时在线人数)", businessType = BusinessType.UPDATE)
    @GetMapping("/update/UseronlineNumber")
    @Login
    public AjaxResult updateProfile(@RequestParam(value = "onlineNumber") String onlineNumber,
                                    @RequestParam(value = "onlineSync",required = false) String onlineSync)
    {
        SysUser user = new SysUser();
        user.setUserId(UserIdUtil.get());
        user.setOnlineNumber(onlineNumber);
        user.setOnlineSync(onlineSync);
        if (userService.updateById(user))
        {
            LoginUser loginUser = userTokenService.getLoginUserToCache();
            // 更新缓存用户信息
            loginUser.getSysUser().setOnlineNumber(user.getOnlineNumber());
            loginUser.getSysUser().setOnlineSync(user.getOnlineSync());
            userTokenService.setLoginUserToCache(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常(用户同时在线人数)，请联系管理员");
    }

}
