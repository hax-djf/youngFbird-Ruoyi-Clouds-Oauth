package com.flybirds.system.sysSocial.key;

import com.flybirds.redis.core.RedisKeyDefine;
import me.zhyd.oauth.model.AuthUser;

import java.time.Duration;

import static com.flybirds.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * System Redis Key 枚举类
 *
 * @author flybirds
 */
public interface RedisKeyConstants {

    RedisKeyDefine SOCIAL_AUTH_USER = new RedisKeyDefine("社交登陆的授权用户",
            "social_auth_user:%d:%s", // 参数为 type，code
            STRING, AuthUser.class, Duration.ofDays(1));

    RedisKeyDefine SOCIAL_AUTH_STATE = new RedisKeyDefine("社交登陆的 state", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "social_auth_state:%s", // 参数为 state
            STRING, String.class, Duration.ofHours(24)); // 值为 state

}
