package com.flybirds.oauth.service.subjoin;


import com.flybirds.common.util.result.Result;

/**
 * login service
 *
 * @author flybirds
 */
public interface LoginSubJoinService {


    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    Result<?> sendLoginSmsCode(String mobile);

}
