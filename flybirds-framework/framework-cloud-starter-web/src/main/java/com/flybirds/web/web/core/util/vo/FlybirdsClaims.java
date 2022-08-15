package com.flybirds.web.web.core.util.vo;

import java.util.HashMap;

/**
 * jwt解析的用户对象
 *
 * @author :flybirds
 */
public class FlybirdsClaims extends HashMap<String, Object> {
    public static String ISSUER = "iss";
    public static String SUBJECT = "sub";
    public static String AUDIENCE = "aud";
    public static String EXPIRATION = "exp"; //单位为秒
    public static String NOT_BEFORE = "nbf";
    public static String ISSUED_AT = "iat";
    public static String ID = "jti";
    public static String OAUTH_TOKEN_SECRETKEY = "oauth_token_secretkey";
    public static String ONLINE_NUMBER = "online_number";
    public static String USER_ID = "user_id";
    public static String USER_NAME = "user_name";
    public static String MOBILE = "mobile";
    public static String SCOPE = "jti";
    public static String USER_AGENT = "user_agent";
    public static String USER_IP = "user_ip";
    public static String AUTHORITIES = "authorities";
    public static String CLIENT_ID = "client_id";
    public static String TENANT_ID = "tenant_id";

    public String buildUserName(){
        return  String.valueOf(this.get(FlybirdsClaims.USER_NAME));
    }

    public Long buildUserId(){
        return  Long.valueOf(String.valueOf(this.get(FlybirdsClaims.USER_ID)));
    }
}
