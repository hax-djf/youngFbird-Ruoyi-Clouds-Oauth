package com.flybirds.system.sysDicData.mq.meesage;

import com.flybirds.mqredis.core.pubsub.ChannelMessage;
import lombok.Data;

/**
 * 字典数据数据刷新 Message
 */
@Data
public class SysDictDataRefreshMessage extends ChannelMessage {

    @Override
    public String getChannel() {
        return "system.dict-data.refresh";
    }

}
