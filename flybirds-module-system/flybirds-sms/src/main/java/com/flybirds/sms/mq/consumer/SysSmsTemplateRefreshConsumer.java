package com.flybirds.sms.mq.consumer;

import com.flybirds.mqredis.core.pubsub.AbstractChannelMessageListener;
import com.flybirds.sms.module.service.SysSmsTemplateService;
import com.flybirds.sms.mq.message.SysSmsTemplateRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SysSmsTemplateRefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class SysSmsTemplateRefreshConsumer extends AbstractChannelMessageListener<SysSmsTemplateRefreshMessage> {

    @Resource
    private SysSmsTemplateService smsTemplateService;

    @Override
    public void onMessage(SysSmsTemplateRefreshMessage message) {
        log.info("[onMessage][收到 SmsTemplate 刷新消息]");
        smsTemplateService.initLocalCache();
    }

}
