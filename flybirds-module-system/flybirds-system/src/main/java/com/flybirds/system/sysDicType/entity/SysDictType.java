package com.flybirds.system.sysDicType.entity;

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
 * 字典类型表 sys_dict_type
 * 
 * @author ruoyi
 */
@TableName(value = "sys_dict_type")
public class SysDictType extends MpBaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @ExcelPOI(name = "字典主键", cellType = ExcelPOI.ColumnType.NUMERIC)
    @TableId(value = "dict_id",type = IdType.AUTO)
    private Long dictId;

    /** 字典名称 */
    @ExcelPOI(name = "字典名称")
    private String dictName;

    /** 字典类型 */
    @ExcelPOI(name = "字典类型")
    private String dictType;

    /** 状态（0正常 1停用） */
    @ExcelPOI(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
