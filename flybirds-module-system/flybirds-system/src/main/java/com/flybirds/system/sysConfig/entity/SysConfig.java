package com.flybirds.system.sysConfig.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flybirds.excel.annotation.ExcelPOI;
import com.flybirds.mybatis.core.basepojo.MpBaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@TableName("sys_config")
public class SysConfig extends MpBaseEntity {


    private static final long serialVersionUID = 1L;

    @ExcelPOI(name = "参数主键", cellType = ExcelPOI.ColumnType.NUMERIC)
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    /** 参数名称 */
    @ExcelPOI(name = "参数名称")
    private String configName;

    /** 参数键名 */
    @ExcelPOI(name = "参数键名")
    private String configKey;

    /** 参数键值 */
    @ExcelPOI(name = "参数键值")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @ExcelPOI(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id", getId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
