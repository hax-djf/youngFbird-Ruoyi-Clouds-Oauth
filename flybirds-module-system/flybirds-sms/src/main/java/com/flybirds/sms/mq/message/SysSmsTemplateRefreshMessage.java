package com.flybirds.sms.mq.message;

import com.flybirds.mqredis.core.pubsub.ChannelMessage;
import lombok.Data;

/**
 * 短信模板的数据刷新 Message
 */
@Data
public class SysSmsTemplateRefreshMessage extends ChannelMessage {

    @Override
    public String getChannel() {
        return "system.sms-template.refresh";
    }

}
