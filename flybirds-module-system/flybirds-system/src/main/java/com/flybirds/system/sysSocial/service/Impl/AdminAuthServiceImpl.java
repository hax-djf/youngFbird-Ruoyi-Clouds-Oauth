package com.flybirds.system.sysSocial.service.Impl;

import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.core.vo.AuthSocialLoginReqVO;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.enums.user.UserTypeEnum;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.result.Result;
import com.flybirds.system.sysSocial.convert.AuthConvert;
import com.flybirds.system.sysSocial.service.AdminAuthService;
import com.flybirds.system.sysSocial.service.SocialUserService;
import com.flybirds.system.sysSocial.vo.AuthSocialBindReqVO;
import com.flybirds.system.sysUser.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.flybirds.common.exception.enums.SysErrorCodeConstants.AUTH_THIRD_LOGIN_NOT_BIND;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.USER_NOT_EXISTS;


/**
 * Auth Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private SocialUserService socialUserService;

    @Resource
    ISysUserService sysUserService;

    @Override
    public void socialBind(Long userId, AuthSocialBindReqVO reqVO) {
        // 绑定社交用户（新增）
        socialUserService.bindSocialUser(AuthConvert.INSTANCE.convert(userId, UserTypeEnum.MEMBER.getValue(), reqVO));
    }

    @Override
    public void socialLoginBind2(Long userId, @Valid AuthSocialLoginReqVO reqVO) {
        socialUserService.bindSocialUser(AuthConvert.INSTANCE.convert(userId, UserTypeEnum.MEMBER.getValue(), reqVO));
    }

    @Override
    public Result<List<KeyValue<String,String>>> socialLogin0(@Valid SocialLoginReqVo0 reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        Long userId = socialUserService.getBindUserId(UserTypeEnum.MEMBER.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (userId == null) {
            return Result.fail(AUTH_THIRD_LOGIN_NOT_BIND);
        }
        SysUser user = sysUserService.selectUserById(userId);
        if (user == null) {
            return Result.fail(USER_NOT_EXISTS);
        }
        // 绑定社交用户（更新）
        socialUserService.bindSocialUser(AuthConvert.INSTANCE.convert(userId, UserTypeEnum.MEMBER.getValue(), reqVO));
        List list = new ArrayList(2);
        KeyValue userName= new KeyValue("userName",user.getUserName());
        KeyValue passWord= new KeyValue("passWord",user.getPassWord());
        list.add(userName);
        list.add(passWord);
        return Result.ok(list);
    }

    @Override
    public Result<String> socialLogin(@Valid AuthSocialLoginReqVO reqVO) {
        Result<String> result = new Result<>();
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        Long userId = socialUserService.getBindUserId(UserTypeEnum.MEMBER.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (userId == null) {
            return Result.fail(AUTH_THIRD_LOGIN_NOT_BIND);
        }
        SysUser user = sysUserService.selectUserById(userId);
        if (user == null) {
            return Result.fail(USER_NOT_EXISTS);
        }
        // 绑定社交用户（更新）
        socialUserService.bindSocialUser(AuthConvert.INSTANCE.convert(userId, UserTypeEnum.MEMBER.getValue(), reqVO));
        result.setData(user.getUserName());
        return result;

    }


}
