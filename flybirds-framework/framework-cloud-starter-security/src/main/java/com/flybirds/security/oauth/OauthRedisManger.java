package com.flybirds.security.oauth;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.redis.manger.RedisCacheManger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * oauth 认证redis 管理
 *
 * @author :flybirds
 */
@Component
@Slf4j
public class OauthRedisManger {

    private volatile static OauthRedisManger oauthRedisManger;

    private final static long EXPIRE_TIME = Constant.Time.TOKEN_EXPIRE * 60;

    private RedisCacheManger redisCacheManger = SpringUtils.getBean(RedisCacheManger.class);

    /* 单例模式 */
    public static OauthRedisManger builder(){

        if (oauthRedisManger == null) {
            synchronized (OauthRedisManger.class) {
                if (oauthRedisManger == null) {
                    oauthRedisManger = new OauthRedisManger();
                }
            }
        }
        return oauthRedisManger;

    };

    /* 获取redisManger */
    public RedisCacheManger buildRedis(){
        return this.redisCacheManger;
    };

    /* 检测是否存在某个key*/
    public boolean exists(String key){
        return this.buildRedis().exists(key);
    }
    /* 获取List中的最后一个数据 */
    public String getCacheListEnd(String key){
        return this.buildRedis().getCacheListEnd(key);
    }
    /* 缓存redis中list数据结构中 */
    public  void setCacheListLeftPush(String key,String accessToken, Long timeExpires){
        this.buildRedis().setCacheListLeftPush(key,accessToken);
        this.buildRedis().expire(key,timeExpires,TimeUnit.SECONDS);
    }
    /* 缓存redis中list数据结构中 */
    public  void setCacheListLeftPush(String key,AuthToken accessToken){
        this.buildRedis().setCacheListLeftPush(key,accessToken.getAccessToken());
        this.buildRedis().expire(key,Long.valueOf(accessToken.getExpires_in()),TimeUnit.SECONDS);
    }
    /* 获取list的长度 */
    public long getCacheListSize(String key) {
        return  this.buildRedis().getCacheListSize(key);
    }
    /* 删除key */
    public boolean delKey(String key){
        return this.buildRedis().deleteObject(key);
    }
    /* 删除指定的 lits数据类型中的数据 */
    public boolean delCacheList(String key,String value){
        return this.buildRedis().deleteCacheListEnd(key,value);
    }

    /* 设置过期时间 */
    public void expireByKey(String key,long EXPIRE_TIME) {
        this.buildRedis().expire(key,EXPIRE_TIME,TimeUnit.SECONDS);
    }
    /* 通过key 获取数据 */
    public String getObjectByKey(String key){
        String redisValue = null;
        Object cacheObject = this.buildRedis().getCacheObject(key);

        if(StringUtils.isNotNull(cacheObject)){
            redisValue =  JSON.parseObject(JSONObject.toJSONString(cacheObject), String.class);
        }else{
            log.info("key 失效",key);
        }
        return redisValue;
    }
    /* 通过key 设置数据 */
    public void setObjectByKey(String key, String accessToken, Long expires, TimeUnit seconds) {
        this.buildRedis().setCacheObject(key,accessToken,expires,seconds);
    }
    /* 在线用户key 和 在线用户数keyList 延长时间 */
    public void expireTimeUserTokenBoth2Keys(String userOauthkey, String userOauthListkey,long EXPIRE_TIME) {

        this.buildRedis().expire(CacheConstantEnum.LOGIN_TOKEN_KEY.buildSuffix(userOauthkey),EXPIRE_TIME,TimeUnit.SECONDS);
        this.buildRedis().expire(CacheConstantEnum.LOGIN_TOKENLIST_KEY.buildSuffix(userOauthListkey),EXPIRE_TIME,TimeUnit.SECONDS);
    }


}



