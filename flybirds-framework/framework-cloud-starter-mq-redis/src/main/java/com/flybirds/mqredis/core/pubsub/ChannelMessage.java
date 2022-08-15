package com.flybirds.mqredis.core.pubsub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flybirds.mqredis.core.message.AbstractRedisMessage;

/**
 * Redis Channel Message 接口
 *
 * @author 芋道源码
 */
public abstract class ChannelMessage extends AbstractRedisMessage{

    /**
     * 获得 Redis Channel
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化
    public abstract String getChannel();

}
