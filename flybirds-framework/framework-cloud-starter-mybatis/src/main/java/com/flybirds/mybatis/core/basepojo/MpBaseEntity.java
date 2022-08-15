package com.flybirds.mybatis.core.basepojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;
import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

/**
 *  全局 Entity基类
 *
 *  @DateTimeFormat 用于将请求参数序列化
 *  @JsonFormat() 将返回参数序列化
 *
 *  @author flybirds
 */
@Data
public abstract class MpBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    @TableField(value = "searchValue",exist = false)
    private String searchValue;

    /** 创建者账号 */
    @TableField(value = "create_name",fill = FieldFill.INSERT)
    protected String createName;

    /**创建者id */
    @TableField(value = "create_user")
    protected Long createUser;

    /** 更新者id */
    @TableField(value = "update_name",fill = FieldFill.INSERT_UPDATE)
    protected String updateName;

    /** 更新者id */
    @TableField(value = "update_user")
    protected Long updateUser;

    //关于序列格式化localDatetime => https://www.cnblogs.com/white-knight/p/8657001.html

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

    /** 备注 */
    @TableField(value = "remark",exist = false)
    protected String remark;

    /**
     * 是否删除 对用用户表示就是注销与未注销
     */
    @TableLogic
    private Boolean delFlag;

    /* 对于get请求操作数据封装*/
    @TableField(value = "params",exist = false)
    private Map<String,Object> params;

    public Map<String, Object> getParams() {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public static final String CREATE_USER = "create_user";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_USER = "update_user";
    public static final String UPDATE_TIME = "update_time";
    public static final String CREATE_NAME = "create_name";
    public static final String UPDATE_NAME = "update_name";
    public static final String REMARK = "remark";

    public void builderUpdateBaseDo(Long userId,String userName){
        userId = userId == null ? 1l : userId;
        userName = userName == null ? "admin" : userName;
        this.setUpdateTime(DateUtils.getNowDate());
        this.setUpdateName(userName);
        this.setUpdateUser(userId);
    }

    public void builderInsertBaseDo(Long userId,String userName){
        userId = userId == null ? 1l : userId;
        userName = userName == null ? "admin" : userName;
        this.setCreateTime(DateUtils.getNowDate());
        this.setUpdateTime(DateUtils.getNowDate());
        this.setCreateName(userName);
        this.setUpdateName(userName);
        this.setCreateUser(userId);
        this.setUpdateUser(userId);
    }
}
