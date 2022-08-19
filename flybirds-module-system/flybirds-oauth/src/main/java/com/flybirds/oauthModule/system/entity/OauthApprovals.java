package com.flybirds.oauthModule.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;
import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

/**
 * 授权阈对象 oauth_approvals
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Data
public class OauthApprovals
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @ExcelPOI(name = "用户id")
    private String userid;

    /** 客户端id */
    @ExcelPOI(name = "客户端id")
    private String clientid;

    /** 授权阈 */
    @ExcelPOI(name = "授权阈")
    private String scope;

    /** 授权阈状态 */
    @ExcelPOI(name = "授权阈状态")
    private String status;

    /** 过期时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    @ExcelPOI(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiresat;

    /** 最后修改时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    @ExcelPOI(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastmodifiedat;

    private Date createTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userid", getUserid())
            .append("clientid", getClientid())
            .append("scope", getScope())
            .append("status", getStatus())
            .append("expiresat", getExpiresat())
            .append("lastmodifiedat", getLastmodifiedat())
            .append("createTime", getCreateTime())
            .toString();
    }
}
