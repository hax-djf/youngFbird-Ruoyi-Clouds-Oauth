package com.flybirds.oauthModule.system.entity;

import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * token刷新对象 oauth_refresh_token
 *
 * @author flybirds
 * @date 2022-07-19
 */
@Data
public class OauthRefreshToken {
    private static final long serialVersionUID = 1L;

    /**
     * 该字段的值是将refresh_token的值通过MD5加密后存储的
     */
    @ExcelPOI(name = "该字段的值是将refresh_token的值通过MD5加密后存储的 ")
    private String tokenId;

    /**
     * 存储将OAuth2RefreshToken.java对象序列化后的二进制数据
     */
    @ExcelPOI(name = "存储将OAuth2RefreshToken.java对象序列化后的二进制数据 ")
    private String token;

    /**
     * 存储将OAuth2Authentication.java对象序列化后的二进制数据
     */
    @ExcelPOI(name = "存储将OAuth2Authentication.java对象序列化后的二进制数据 ")
    private String authentication;

    private Date createTime;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tokenId", getTokenId())
                .append("token", getToken())
                .append("authentication", getAuthentication())
                .append("createTime", getCreateTime())
                .toString();
    }
}
