package com.flybirds.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis序列化配置
 *
 * @author ruoyi
 */
@Configuration
@EnableCaching
public class FlybirdsRedisAutoConfiguration {
    /**
     *  redis的配置信息=>fastjosn序列化
     * @param connectionFactory
     * @return
     */
    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes", "deprecation" })//忽略警告信息
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        template.setValueSerializer(serializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
//
//    /**
//     * redis的配置信息 => Jackson 序列化
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        // 创建 RedisTemplate 对象
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        // 设置 RedisConnection 工厂。😈 它就是实现多种 Java Redis 客户端接入的秘密工厂。感兴趣的胖友，可以自己去撸下。
//        template.setConnectionFactory(factory);
//        // 使用 String 序列化方式，序列化 KEY 。
//        template.setKeySerializer(RedisSerializer.string());
//        template.setHashKeySerializer(RedisSerializer.string());
//        // 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
//        template.setValueSerializer(RedisSerializer.json());
//        template.setHashValueSerializer(RedisSerializer.json());
//        return template;
//    }
}
