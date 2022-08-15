package com.flybirds.oauth.manger.login;


import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.LoginReqVo0;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * login Handler 服务
 *
 * @author flybirds
 */
public interface UserLoginHandler0<T extends LoginReqVo0> {

    /**
     * @since  1.0
     * 校验
     *
     * @param body
     */
    void vaild(T body);

    /**
     *  @since 1.0
     *  用户登录Oauth2.0 获取 token
     *
     * @param body
     * @return
     */
    AuthToken login0(T body);

    /**
     * @since 1.0
     * 获取用户
     *
     * @param userName
     * @return
     */
    UserDetails aroundUserDetails(String userName);

    /**
     * @since  1.0
     * 异常处理
     *
     * @param authToken
     * @param body
     * @return
     */
     Exception afterException(AuthToken authToken,T body,Exception e);

    /**
     * @since  1.0
     * 任务提交
     *
     * @param authToken
     */
    void  afterCommitTask(AuthToken authToken);


}
