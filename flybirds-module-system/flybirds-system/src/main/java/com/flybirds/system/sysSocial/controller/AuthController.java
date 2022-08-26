package com.flybirds.system.sysSocial.controller;

import com.flybirds.api.core.vo.AuthSocialLoginReqVO;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.enums.user.UserTypeEnum;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.result.Result;
import com.flybirds.system.sysSocial.convert.AuthConvert;
import com.flybirds.system.sysSocial.entity.SysSocialUser;
import com.flybirds.system.sysSocial.service.AdminAuthService;
import com.flybirds.system.sysSocial.service.SocialUserService;
import com.flybirds.system.sysSocial.vo.AuthSocialBindReqVO;
import com.flybirds.system.sysSocial.vo.AuthSocialUnbindReqVO;
import com.flybirds.web.web.annotation.Login;
import com.flybirds.web.web.annotation.OauthServiceSignature;
import com.flybirds.web.web.core.util.user.UserIdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;

import static com.flybirds.common.util.result.Result.ok;

@Api(tags = "社交登录")
@RestController
@RequestMapping("/social")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;

    @Resource
    private SocialUserService socialUserService;
    
    @GetMapping("/open/social-auth-redirect")
    @ApiOperation("社交授权的跳转")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "社交类型", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "redirectUri", value = "回调路径", dataTypeClass = String.class)
    })
    public Result<String> socialAuthRedirect(@RequestParam("type") Integer type,
                                             @RequestParam("redirectUri") String redirectUri) {
        return ok(socialUserService.getAuthorizeUrl(type, redirectUri),"社交授权的跳转获取成功");
    }


    @PostMapping("/open/social-login")
    @ApiOperation("社交登录（非真正的社交登录操作），使用 code 授权码，校验是否存在用户")
    @OauthServiceSignature
    public Result<String> socialLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return authService.socialLogin(reqVO);

    }

    @PostMapping("/open/social-login0")
    @ApiOperation("社交登录（非真正的社交登录操作），使用 code 授权码，校验是否存在用户")
    @OauthServiceSignature
    public Result<List<KeyValue<String,String>>> socialLogin(@RequestBody @Valid SocialLoginReqVo0 reqVO) {
        return authService.socialLogin0(reqVO);

    }

    @PostMapping("/open/social-bind/{userId}")
    @ApiOperation("社交登录，使用 code 授权码 + 账号密码")
    public Result<Boolean> socialLoginBind2(@RequestBody @Valid SocialLoginReqVo0 reqVO,
                                        @PathVariable(value = "userId") Long userId) {
        authService.socialLoginBind2(userId, AuthConvert.INSTANCE.convert(reqVO));
        return ok(true);
    }

    @PostMapping("/social-bind")
    @ApiOperation("社交绑定，使用 code 授权码")
    @Login
    public Result<Boolean> socialBind(@RequestBody @Valid AuthSocialBindReqVO reqVO) {
        authService.socialBind(UserIdUtil.get(), reqVO);
        return ok(true,"社交绑定成功");
    }

    @PostMapping("/social-unbind")
    @ApiOperation("取消社交绑定")
    @Login
    public Result<Boolean> socialUnbind(@RequestBody AuthSocialUnbindReqVO reqVO) {
        socialUserService.unbindSocialUser(UserIdUtil.get(),
                UserTypeEnum.MEMBER.getValue(), reqVO.getType(), reqVO.getUnionId());
        return ok(true,"取消社交绑定成功");
    }

    @GetMapping("/social-user-list")
    @ApiOperation("获取social用户列表信息")
    @Login
    public Result<List<SysSocialUser>> socialAuthRedirect() {

        List<SysSocialUser> socialUserList = socialUserService.getSocialUserList(UserIdUtil.get(), UserTypeEnum.MEMBER.getValue());
        return ok(socialUserList);
    }

}
