package com.flybirds.system.sysSocial.service;


import com.flybirds.api.core.vo.AuthSocialLoginReqVO;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.result.Result;
import com.flybirds.system.sysSocial.vo.AuthSocialBindReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台的认证 Service 接口
 *
 * 提供用户的账号密码登录、token 的校验等认证相关的功能
 *
 * @author 芋道源码
 */
public interface AdminAuthService {

    /**
     * 社交绑定，使用 code 授权码
     *
     * @param userId 用户编号
     * @param reqVO 绑定信息
     */
    void socialBind(Long userId, @Valid AuthSocialBindReqVO reqVO);

    /**
     * 社交登录，使用 code 授权码，校验是否存在用户 存在返回用户进行登录
     * @param reqVO
     * @return
     */
    Result<String> socialLogin(@Valid AuthSocialLoginReqVO reqVO);


    /**
     * 社交登录，使用 code 授权码 + 账号密码
     *
     * @param reqVO 登录信息
     */
    void socialLoginBind2(Long userId, @Valid AuthSocialLoginReqVO reqVO);

    Result<List<KeyValue<String,String>>> socialLogin0(@Valid SocialLoginReqVo0 reqVO);
}
