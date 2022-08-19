package com.flybirds.dict.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;
import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

/**
 * 字典数据DTO
 *
 * @author ruoyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictDataCacheDTO {

    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    private Long dictCode;

    /** 字典排序 */
    private Long dictSort;

    /** 字典标签 */
    private String dictLabel;

    /** 字典键值 */
    private String dictValue;

    /** 字典类型 */
    private String dictType;

    /** 样式属性（其他样式扩展） */
    private String cssClass;

    /** 表格字典样式 */
    private String listClass;

    /** 是否默认（Y是 N否） */
    private String isDefault;

    /** 状态（0正常 1停用） */
    private String status;

    /** 搜索值 */
    private String searchValue;

    /** 创建者账号 */
    private String createBy;

    /** 更新者账号 */
    private String updateBy;

    /** 创建者id*/
    private long createUser;

    /**更新者id*/
    private long updateUser;

    /** 创建时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    private Date createTime;

    /** 备注 */
    private String remark;

    /** 更新时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    private Date updateTime;
}
