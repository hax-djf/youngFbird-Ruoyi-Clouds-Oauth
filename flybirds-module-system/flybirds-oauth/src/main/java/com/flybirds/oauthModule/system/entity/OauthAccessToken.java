package com.flybirds.oauthModule.system.entity;

import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 用户token信息对象 oauth_access_token
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Data
public class OauthAccessToken
{
    private static final long serialVersionUID = 1L;

    /** 从服务器端获取到的access_token的值*/
    @ExcelPOI(name = "从服务器端获取到的access_token的值")
    private String tokenId;

    /** 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据 */
    @ExcelPOI(name = "存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据")
    private String token;

    /** 该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的.具体实现请参考DefaultClientKeyGenerator.java类. */
    private String authenticationId;

    /** 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id*/

    @ExcelPOI(name = "若客户端没有用户名(如grant_type=client_credentials则该值等于client_id")
    private String userName;

    /** 客户端id */
    @ExcelPOI(name = "客户端id")
    private String clientId;

    /** 存储将OAuth2Authentication.java对象序列化后的二进制数据*/
    @ExcelPOI(name = "存储将OAuth2Authentication.java对象序列化后的二进制数据")
    private String authentication;

    /** 该字段的值是将refresh_token的值通过MD5加密后存储的*/
    @ExcelPOI(name = "该字段的值是将refresh_token的值通过MD5加密后存储的")
    private String refreshToken;

    private Date createTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tokenId", getTokenId())
            .append("token", getToken())
            .append("authenticationId", getAuthenticationId())
            .append("userName", getUserName())
            .append("clientId", getClientId())
            .append("authentication", getAuthentication())
            .append("refreshToken", getRefreshToken())
            .append("createTime", getCreateTime())
            .toString();
    }
}
