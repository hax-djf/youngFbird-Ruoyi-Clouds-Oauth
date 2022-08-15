package com.flybirds.oauthModule.system.entity;

import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 授权码信息对象 oauth_code
 *
 * @author flybirds
 * @date 2022-07-19
 */
@Data
public class OauthCode {
    private static final long serialVersionUID = 1L;

    /**
     * 存储服务端系统生成的code的值(未加密)
     */
    @ExcelPOI(name = "存储服务端系统生成的code的值(未加密) ")
    private String code;

    /**
     * 存储将AuthorizationRequestHolder.java对象序列化后的二进制数据
     */
    @ExcelPOI(name = "存储将AuthorizationRequestHolder.java对象序列化后的二进制数据")
    private String authentication;

    private Date createTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("createTime", getCreateTime())
                .append("code", getCode())
                .append("authentication", getAuthentication())
                .toString();
    }
}
