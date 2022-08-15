package com.flybirds.oauth.manger.task;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.security.model.LoginTask;
import com.lmax.disruptor.dsl.Disruptor;
/**
 *  service 令牌申请以后，需要实现的存储操作
 *
 * @author :flybirds
 */
public interface LoginTaskHandler0 {


    void submitTask(AuthToken authToken, AccountLoginChannelEnum channel);
    /**
     * 获取对用登录完成之后的disruptor
     * @return
     */
      Disruptor<LoginTask> getDisruptor();

}
