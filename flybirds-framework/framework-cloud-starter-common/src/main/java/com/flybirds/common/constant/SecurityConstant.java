package com.flybirds.common.constant;

/**
 * 认证权限相关通用常量
 *
 * @author flybirds
 */
public class SecurityConstant {

    /**
     * 令牌自定义标识
     */
    public static final String HEADER = "Authorization";

    /**
     * 浏览器信息传递标识 User-Agent
     */
    public static final String USER_AGENT = "User-Agent";

    /**
     * 自定义登录传递 ip地址信息
     */
    public static final String USER_IP = "User-Ip";

    /**
     * 权限标识标识
     */
    public static final String AUTHORITIES = "authorities";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 服务之间访问令牌
     */
    public static final String SERVICE_SIGNATURE = "Service-Signature";
    public static final String SERVICE_SIGNATURE_VALUE = "916lWh2WMcbSWiHv@flybirds";

    /**
     * 认证方式
     */
    public static final String TOKEN_TYPE = "Basic ";

    /**
     * 权限数据
     */
    public static final String OAUTH_TOKEN_SECRETKEY = "oauth_token_secretkey";


    public static final String OAUTH_USER_AGENT = "user_agent";

    public static final String OAUTH_USER_IP = "user_ip";

    /**
     * 认证用户
     */
    public static final String OAUTH_USERNAME = "username";

    /**
     * 认证密码
     */
    public static final String OAUTH_PASSWORD = "password";

    /**
     * 短信验证码
     */
    public static final String OAUTH_MOBILE_CODE = "mobile_code";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段【不可更改用于在令牌中字段存储】
     */
    public static final String DETAILS_USERNAME = "user_name";

    /**
     * 手机号码
     */
    public static final String DETAILS_MOBILE = "mobile";

    /**
     * 用户登录数量
     */
    public static final String DETAILS_ONLINE_NUMBER = "online_number";

    /**
     * 租户id 设置到jwt中
     */
    public static final String DETAILS_TENANT_ID = "tenant_id";


    /**
     * sys_oauth_client_details 表的字段，不包括client_id、client_secret
     */
    public static final String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    public static final String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS + " from oauth_client_details";

    /**
     * 按条件client_id 查询
     */
    public static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    /**
     * 默认的查询语句
     */
    public static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";
}
