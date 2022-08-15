package com.flybirds.system.sysLoginInfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统访问记录表 sys_logininfor
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_logininfor")
public class SysLogininfor  implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ExcelPOI(name = "序号", cellType = ExcelPOI.ColumnType.NUMERIC)
    @TableId
    private Long infoId;

    /** 用户账号 */
    @ExcelPOI(name = "用户账号")
    private String userName;

    /** 登录状态 0成功 1失败 */
    @ExcelPOI(name = "登录/退出状态", readConverterExp = "0=成功,1=失败")
    private String status;

    /** 登录IP地址 */
    @ExcelPOI(name = "登录地址")
    private String ipaddr;

    /** 登录地点 */
    @ExcelPOI(name = "登录地点")
    private String loginLocation;

    /** 浏览器类型 */
    @ExcelPOI(name = "浏览器")
    private String browser;

    /** 操作系统 */
    @ExcelPOI(name = "操作系统")
    private String os;

    /** 提示消息 */
    @ExcelPOI(name = "提示消息")
    private String msg;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelPOI(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /* 对于get请求操作数据封装*/
    @TableField(value = "params",exist = false)
    private Map<String,Object> params;

    @TableField(value = "type")
    @ExcelPOI(name = "账号操作类型",readConverterExp = "0=登录,1=退出,2=未知")
    private String type;

    /** 搜索值 */
    @TableField(value = "searchValue",exist = false)
    private String searchValue;

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
}
