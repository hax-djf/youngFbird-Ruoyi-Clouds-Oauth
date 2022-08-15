package com.flybirds.system.sysSocial.convert;

import com.flybirds.api.core.vo.AuthSocialLogin2ReqVO;
import com.flybirds.api.core.vo.AuthSocialLoginReqVO;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.system.sysSocial.dto.SocialUserBindReqDTO;
import com.flybirds.system.sysSocial.dto.SocialUserUnbindReqDTO;
import com.flybirds.system.sysSocial.vo.AuthSocialBindReqVO;
import com.flybirds.system.sysSocial.vo.AuthSocialUnbindReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialBindReqVO reqVO);
    SocialUserBindReqDTO convert(Long userId, Integer userType, SocialLoginReqVo0 reqVO);
    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialLogin2ReqVO reqVO);
    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialLoginReqVO reqVO);
    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AuthSocialUnbindReqVO reqVO);
    AuthSocialLoginReqVO convert(SocialLoginReqVo0 reqVo0);
}
