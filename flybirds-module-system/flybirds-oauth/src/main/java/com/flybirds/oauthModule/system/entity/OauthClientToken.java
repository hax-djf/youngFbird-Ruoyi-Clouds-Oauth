package com.flybirds.oauthModule.system.entity;

import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 客户端token对象 oauth_client_token
 *
 * @author flybirds
 * @date 2022-07-19
 */
@Data
public class OauthClientToken {
    private static final long serialVersionUID = 1L;

    /**
     * 从服务器端获取到的access_token的值.
     */
    @ExcelPOI(name = "从服务器端获取到的access_token的值")
    private String tokenId;

    /**
     * 这是一个二进制的字段, 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据
     */
    @ExcelPOI(name = "这是一个二进制的字段, 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据")
    private String token;

    /**
     * 该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultClientKeyGenerator.java类.
     */
    private String authenticationId;

    /**
     * 登录时的用户名
     */
    @ExcelPOI(name = "登录时的用户名")
    private String userName;

    /**
     * $column.columnComment
     */

    @ExcelPOI(name = "登录时的用户名 ")
    private String clientId;

    private Date createTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tokenId", getTokenId())
                .append("token", getToken())
                .append("authenticationId", getAuthenticationId())
                .append("userName", getUserName())
                .append("clientId", getClientId())
                .append("createTime", getCreateTime())
                .toString();
    }
}
