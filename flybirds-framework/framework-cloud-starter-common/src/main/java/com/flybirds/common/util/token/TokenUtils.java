package com.flybirds.common.util.token;

import cn.hutool.core.util.StrUtil;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.SecurityConstant;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 用户 token 工具类
 * 
 * @author :flybirds
 */
public class TokenUtils {

    /* 获取到令牌存在的redis中的key */
    public static String getTokenRedisKey(String token) {
        return CacheConstantEnum.OAUTH_ACCESS_ACCESS.buildSuffix(token);
    }

    /* 获取请求token请求头数据 */
    public static String getToken(ServerHttpRequest request) {
        /**
         * 获取请求头的信息
         * getFirst-->不是获取到第一个请求头的信息，就是获取请求头中的这个参数而已
         */
        String token = request.getHeaders().getFirst(SecurityConstant.HEADER);

        if (StrUtil.isNotBlank(token)
                && token.startsWith(SecurityConstant.TOKEN_PREFIX)) {

            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        }

        return token;
    }
    
    /** 获取请求token请求头数据*/
    public static String getToken(String token) {

        if (StrUtil.isNotBlank(token)
                && token.startsWith(SecurityConstant.TOKEN_PREFIX)) {

            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        }

        return token;
    }
}
