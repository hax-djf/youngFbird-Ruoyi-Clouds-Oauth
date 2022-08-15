package com.flybirds.oauth.core;

import com.flybirds.api.RemoteUserService;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.result.Result;
import com.flybirds.oauth.exception.CustomOauthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.flybirds.common.constant.SecurityConstant.OAUTH_PASSWORD;
import static com.flybirds.common.constant.SecurityConstant.OAUTH_USERNAME;


/**
 * 用户查询操作中转类
 *
 * @author :flybirds
 */
@Component(value = "Oauth2RemoteUserService")
public class Oauth2RemoteUserService {

    @Autowired(required = false)
    RemoteUserService remoteUserService;

    public void formatMobileTopassword( Map<String, String> parameters){

        String mobile =  parameters.get(OAUTH_USERNAME);
        AssertUtil.isNotNull(mobile,new CustomOauthException("mobile code is not  null"));
        Result<SysUser> result = remoteUserService.getUserByMobile(mobile);;

        if (result.onSuccess()) {
            SysUser user = result.getData();
            /**
             * 设置username和password，之所以设置手机号
             * 在loadUserByUsername方法中和原本走密码模式的username做一个区分
             */
            parameters.put(OAUTH_USERNAME, user.getPhonenumber());
            parameters.put(OAUTH_PASSWORD, user.getPassWord());
        }else{
            throw new CustomOauthException("mobile is not registered to userTable");
        }

    }

    public void formatSocialTopassword(Map<String, String> parameters) {
        String userName = parameters.get(OAUTH_USERNAME);
        String passWord = parameters.get(OAUTH_PASSWORD);
        AssertUtil.isNotNull(userName,new CustomOauthException("userName is not null"));
        AssertUtil.isNotNull(passWord,new CustomOauthException("passWord is not null"));
        parameters.put(OAUTH_USERNAME, userName);
        parameters.put(OAUTH_PASSWORD, passWord);

    }
}
