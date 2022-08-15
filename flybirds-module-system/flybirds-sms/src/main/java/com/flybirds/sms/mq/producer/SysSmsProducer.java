package com.flybirds.sms.mq.producer;

import com.flybirds.common.util.core.KeyValue;
import com.flybirds.mqredis.core.util.RedisMQTemplate;
import com.flybirds.sms.mq.message.SysSmsChannelRefreshMessage;
import com.flybirds.sms.mq.message.SysSmsSendMessage;
import com.flybirds.sms.mq.message.SysSmsTemplateRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sms 短信相关消息的 Producer
 *
 * @author zzf
 * @date 2021/3/9 16:35
 */
@Slf4j
@Component
public class SysSmsProducer {


    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 发送 {@link SysSmsSendMessage} 消息
     *
     * @param logId 短信日志编号
     * @param mobile 手机号
     * @param channelId 渠道编号
     * @param apiTemplateId 短信模板编号
     * @param templateParams 短信模板参数
     */
    public void sendSmsSendMessage(Long logId, String mobile,
                                   Long channelId, String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
        SysSmsSendMessage message = new SysSmsSendMessage().setLogId(logId).setMobile(mobile);
        message.setChannelId(channelId)
                .setApiTemplateId(apiTemplateId)
                .setTemplateParams(templateParams);
        redisMQTemplate.send(message);
    }

    /**
     * 发送 {@link SysSmsSendMessage} 消息
     */
    public void sendSmsSendMessage(SysSmsSendMessage message) {
        redisMQTemplate.send(message);
    }

    /**
     * 发送 {@link SysSmsChannelRefreshMessage} 消息
     */
    public void sendSmsChannelRefreshMessage() {
        SysSmsChannelRefreshMessage message = new SysSmsChannelRefreshMessage();
        redisMQTemplate.send(message);
    }

    /**
     * 发送 {@link SysSmsTemplateRefreshMessage} 消息
     */
    public void sendSmsTemplateRefreshMessage() {
        SysSmsTemplateRefreshMessage message = new SysSmsTemplateRefreshMessage();
        redisMQTemplate.send(message);
    }

}
