//package com.flybirds.mqredis.core.util;
//
//import com.flybirds.common.util.json.jackson.JsonUtil;
//import com.flybirds.mqredis.core.pubsub.ChannelMessage;
//import com.flybirds.mqredis.core.stream.StreamMessage;
//import org.springframework.data.redis.connection.stream.RecordId;
//import org.springframework.data.redis.connection.stream.StreamRecords;
//import org.springframework.data.redis.core.RedisTemplate;
//
///**
// * Redis 消息工具类
// *
// * @author flybirds
// */
//public class RedisMessageUtils {
//
//    /**
//     * 发送 Redis 消息，基于 Redis pub/sub 实现
//     *
//     * @param redisTemplate Redis 操作模板
//     * @param message 消息
//     */
//    public static <T extends ChannelMessage> void sendChannelMessage(RedisTemplate<?, ?> redisTemplate, T message) {
//        redisTemplate.convertAndSend(message.getChannel(), JsonUtil.toJsonString(message));
//    }
//
//    /**
//     * 发送 Redis 消息，基于 Redis Stream 实现
//     *
//     * @param redisTemplate Redis 操作模板
//     * @param message 消息
//     * @return 消息记录的编号对象
//     */
//    public static <T extends StreamMessage> RecordId sendStreamMessage(RedisTemplate<String, ?> redisTemplate, T message) {
//        return redisTemplate.opsForStream().add(StreamRecords.newRecord()
//                .ofObject(JsonUtil.toJsonString(message)) // 设置内容
//                .withStreamKey(message.getStreamKey())); // 设置 stream key
//    }
//
//}
