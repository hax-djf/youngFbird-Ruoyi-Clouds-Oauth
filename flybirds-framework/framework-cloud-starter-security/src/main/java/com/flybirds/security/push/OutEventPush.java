package com.flybirds.security.push;

import com.flybirds.common.enums.login.AccountLoginOutChannelEnum;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.security.factory.Oauth2OutFactory;
import com.flybirds.security.mange.LoginOutHandler0;
import com.flybirds.security.model.OutTask;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于Disruptor，通过不同的认证方式进行Disruptor进行任务发布
 *
 * @author : flybirds
 */
@Slf4j
public class OutEventPush {

    /**
     * disruptor转化类，进行自定义传输数据封装
     */
    private final static EventTranslatorVararg<OutTask> translator = (OutTask loginOutTask, long taskid, Object... objs) ->{
        /**
         * 数据封装
         * 参数从object[0]开始
         */
        loginOutTask.setTokenValue((String) objs[0]);
        loginOutTask.setOutChannel((AccountLoginOutChannelEnum) objs[1]);
    };

    public static final void send(String tokenValue, AccountLoginOutChannelEnum outChannel) {

        AssertUtil.isNotNull(tokenValue,String.format(" %s 退出任务提交失败",outChannel.getLabel()));
        Disruptor<OutTask> disruptor = getDisruptorByGrantType(outChannel);
        RingBuffer ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent(translator,tokenValue,outChannel);
    }

    protected static Disruptor<OutTask> getDisruptorByGrantType(AccountLoginOutChannelEnum outChannel ){

        LoginOutHandler0 loginOutHandler = Oauth2OutFactory.get0(outChannel);

        return  loginOutHandler.getDisruptor();
    }

    public static void EventpushUserExit(String tokenValue, AccountLoginOutChannelEnum outChannel) {
        send(tokenValue,outChannel);
    }
}
