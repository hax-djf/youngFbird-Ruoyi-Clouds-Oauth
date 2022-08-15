package com.flybirds.oauthModule.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;
import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

/**
 * 应用信息app对象 oauth_app_details
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Data
public class OauthAppDetails
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 应用名称 */
    @ExcelPOI(name = "应用名称")
    private String appName;

    /** 应用logo图片 */
    @ExcelPOI(name = "应用logo图片")
    private String appLogo;

    /** 应用描述 */
    @ExcelPOI(name = "应用描述")
    private String appNote;

    /** 应用主页地址 */
    @ExcelPOI(name = "应用主页地址")
    private String appIndex;

    /** 应用所对应的客户端id，外键 */
    @ExcelPOI(name = "应用所对应的客户端id，外键")
    private String appClientId;

    /** 创建者账号 */
    @TableField(value = "create_name",fill = FieldFill.INSERT)
    protected String createName;

    /** 更新者id */
    @TableField(value = "update_name",fill = FieldFill.INSERT_UPDATE)
    protected String updateName;

    /** 创建时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @ExcelPOI(name = "创建时间")
    protected Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    @ExcelPOI(name = "修改时间")
    protected Date updateTime;

    /**
     * 是否删除 对用用户表示就是注销与未注销
     */
    @TableLogic
    private Boolean delFlag;

    //客户端信息
    private OauthClientDetails oauthClientDetails;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("appName", getAppName())
            .append("appLogo", getAppLogo())
            .append("appNote", getAppNote())
            .append("appIndex", getAppIndex())
            .append("appClientId", getAppClientId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createName", getCreateName())
            .append("updateName", getUpdateName())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
