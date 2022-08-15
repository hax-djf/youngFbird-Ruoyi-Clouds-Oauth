package com.flybirds.oauth.push;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.enums.login.AccountLoginStatusEnum;
import com.flybirds.oauth.factory.LoginTaskFactory;
import com.flybirds.security.model.LoginTask;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * 基于Disruptor，通过不同的认证方式进行Disruptor进行任务发布
 *
 * @author :flybrids
 */
@Slf4j
public class LoginEventPush {

    /* disruptor转化类，进行自定义传输数据封装 */
    private final static EventTranslatorVararg<LoginTask> translator = (LoginTask loginTask, long taskid, Object... objs) ->{
        /**
         * 数据封装
         * 参数从object[0]开始
         */
        loginTask.setAuthToken((AuthToken) objs[0]);
        loginTask.setLoginStatus((AccountLoginStatusEnum) objs[1]);
        loginTask.setLoginChannel((AccountLoginChannelEnum) objs[2]);
    };

    private static final void send(AuthToken authToken,AccountLoginStatusEnum status,AccountLoginChannelEnum channel) {

        Disruptor<LoginTask> disruptor = getDisruptorByGrantType(status);

        RingBuffer ringBuffer = disruptor.getRingBuffer();

        ringBuffer.publishEvent(translator,authToken,status,channel);
    }

    private static Disruptor<LoginTask> getDisruptorByGrantType(AccountLoginStatusEnum status){
        return  LoginTaskFactory.get0(status).getDisruptor();
    }

    /* 任务提交 */
    public static void EventpushLoginTask(Function<AuthToken,Boolean> booleanFunction,
                                          AuthToken authToken,
                                          String username,
                                          AccountLoginChannelEnum channel,
                                          AccountLoginStatusEnum status) {

        log.info("用户 [ {} ]"+ status.getLabel() ,username);
        if(booleanFunction.apply(authToken)){
            log.info("用户提交 [ {} ] 任务队列"+ status.getLabel(),authToken.getUser_name());
            send(authToken,status,channel);
        }
    }

}
