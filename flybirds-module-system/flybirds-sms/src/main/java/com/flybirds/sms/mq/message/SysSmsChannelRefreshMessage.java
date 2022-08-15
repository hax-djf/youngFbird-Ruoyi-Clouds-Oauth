package com.flybirds.sms.mq.message;

import com.flybirds.mqredis.core.pubsub.ChannelMessage;
import lombok.Data;

/**
 * 短信渠道的数据刷新 Message
 */
@Data
public class SysSmsChannelRefreshMessage extends ChannelMessage {

    @Override
    public String getChannel() {
        return "system.sms-channel.refresh";
    }

}
