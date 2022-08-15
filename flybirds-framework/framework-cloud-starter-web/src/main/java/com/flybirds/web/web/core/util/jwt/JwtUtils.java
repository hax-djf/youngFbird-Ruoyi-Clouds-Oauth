package com.flybirds.web.web.core.util.jwt;

import com.alibaba.fastjson.JSON;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.util.json.jackson.JsonUtil;
import com.flybirds.web.web.core.util.vo.FlybirdsClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * jwt解析工具类
 *
 * @author flybirds
 */
@Component
@Slf4j
public class JwtUtils {

    private static final String PUBLIC_KEY = "public.key";

    private static final String header = "Authorization";

    private static final Long unit = 1000l;
    /**
     * 生成jwt token
     */
    public static String generateToken(long userId) {
        //证书文件路径
        String key_location="flybirds.jks";
        //秘钥库密码
        String key_password="flybirds";
        //秘钥密码
        String keypwd = "flybirds";
        //秘钥别名
        String alias = "flybirds";
        //访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);
        //创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,key_password.toCharArray());
        //读取秘钥对(公钥、私钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypwd.toCharArray());
        //获取私钥
        RSAPrivateKey rsaPrivate = (RSAPrivateKey) keyPair.getPrivate();
        //定义Payload
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "1");
        tokenMap.put("name", "itheima");
        tokenMap.put("roles", "ROLE_VIP,ROLE_USER");
        //生成Jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivate));
        //取出令牌
        return  jwt.getEncoded();
    }

    /**
     * 令牌解析
     * @param token
     * @return
     */
    public static FlybirdsClaims getClaimByToken(String token) {
        FlybirdsClaims flybirdsClaims = null;
        try {
            Jwt jwt = JwtHelper.decodeAndVerify(token.replace(SecurityConstant.TOKEN_PREFIX, ""), new RsaVerifier(getPubKey()));
            flybirdsClaims = JsonUtil.parseObject(jwt.getClaims(), FlybirdsClaims.class);
        } catch (Exception e) {
            log.debug("validate is token com.tduck.cloud.wx.mp.error ", e);
            return null;
        }
        return flybirdsClaims;
    }

    //获取公钥
    private static String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }
    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(Long expiration) {
        //todo 注意会会出现失效的情况
        return expiration * unit < (new Date().getTime());
    }

    public static String getHeader() {
        return header;
    }
}
