package com.flybirds.sms.mq.consumer;

import com.flybirds.mqredis.core.pubsub.AbstractChannelMessageListener;
import com.flybirds.sms.module.service.SysSmsService;
import com.flybirds.sms.mq.message.SysSmsSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SysSmsSendMessage} 的消费者
 *
 * @author zzf
 * @date 2021/3/9 16:35
 */
@Component
@Slf4j
public class SysSmsSendConsumer extends AbstractChannelMessageListener<SysSmsSendMessage> {

    @Resource
    private SysSmsService smsService;

    @Override
    public void onMessage(SysSmsSendMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        smsService.doSendSms(message);
    }

}
