package com.flybirds.security.model;


import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.enums.login.AccountLoginStatusEnum;
import lombok.Builder;
import lombok.Data;

/**
 * 用户登录任务模型
 *
 * @author :flybirds
 */
@Data
@Builder
public class LoginTask {
    /**
     * 目前制作队列的一种形式，数据不做存储
     */
    private AuthToken authToken;

    /**
     * 登录状态 {@link AccountLoginStatusEnum}
     */
    private AccountLoginStatusEnum loginStatus;

    /**
     * 登录模式 {@link AccountLoginChannelEnum}
     */
    private AccountLoginChannelEnum loginChannel;

}
