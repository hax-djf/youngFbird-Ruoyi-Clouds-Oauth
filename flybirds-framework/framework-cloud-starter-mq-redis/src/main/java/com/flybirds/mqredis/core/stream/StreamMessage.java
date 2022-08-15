package com.flybirds.mqredis.core.stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flybirds.mqredis.core.message.AbstractRedisMessage;

/**
 * Redis Stream Message 接口
 *
 * @author 芋道源码
 */
public abstract class StreamMessage extends AbstractRedisMessage {

    /**
     * 获得 Redis Stream Key
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化
    public abstract String getStreamKey();

}
