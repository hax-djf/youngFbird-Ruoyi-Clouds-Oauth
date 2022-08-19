package com.flybirds.sms.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flybirds.excel.annotation.ExcelPOI;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;
import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

/**
 * @author :flybirds
 * @create :2021-06-30 20:45:00
 * @description :短信模板
 *
 * 注意点： 关于JacksonTypeHandler 在mybatis_plus不生效的情况的，请注意
 *
 * 在@TableName加上 autoResultMap = true
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_sms_template",autoResultMap = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysSmsTemplate  implements Serializable {

    @ApiModelProperty(name = "id" , value = "主键")
    @TableId(value = "id",type= IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "短信类型", required = true, example = "1", notes = "参见 SysSmsTemplateTypeEnum 枚举类")
    @NotNull(message = "短信类型不能为空")
    @ExcelPOI(name = "短信签名")
    private Integer type;

    @ApiModelProperty(value = "开启状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举类")
    @NotNull(message = "开启状态不能为空")
    @ExcelPOI(name = "开启状态")
    private Integer status;

    @ApiModelProperty(value = "模板编码", required = true, example = "test_01")
    @NotNull(message = "模板编码不能为空")
    @ExcelPOI(name = "模板编码")
    private String code;

    @ApiModelProperty(value = "模板名称", required = true, example = "yudao")
    @NotNull(message = "模板名称不能为空")
    @ExcelPOI(name = "模板名称")
    private String name;

    @ApiModelProperty(value = "模板内容", required = true, example = "你好，{name}。你长的太{like}啦！")
    @NotNull(message = "模板内容不能为空")
    @ExcelPOI(name = "模板内容")
    private String content;

    @ApiModelProperty(value = "短信 API 的模板编号", required = true, example = "4383920")
    @NotNull(message = "短信 API 的模板编号不能为空")
    @ExcelPOI(name = "短信 API 的模板编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private String apiTemplateId;

    @ApiModelProperty(value = "短信渠道编号", required = true, example = "10")
    @NotNull(message = "短信渠道编号不能为空")
    @ExcelPOI(name = "短信渠道编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long channelId;
    /**
     * 短信渠道编码
     *
     * 冗余 {@link SysSmsChannel#getCode()}
     */
    @ApiModelProperty(value = "短信渠道编码", required = true, example = "10")
    @NotNull(message = "短信渠道编码不能为空")
    @ExcelPOI(name = "短信渠道编码")
    private String channelCode;

    /**
     * 参数数组(自动根据内容生成)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;

    @ApiModelProperty(value = "逻辑删除 0 表示正常，1表示删除",name = "delete_status")
    @TableField(value = "delete_status")
    //@TableLogic 不使用逻辑删除操作
    private Integer deleteStatus;

    /** 创建者账号 */
    @TableField(value = "create_name",fill = FieldFill.INSERT)
    protected String createName;

    /**创建者id */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    protected Long createUser;

    /** 更新者id */
    @TableField(value = "update_name",fill = FieldFill.UPDATE)
    protected String updateName;

    /** 更新者id */
    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    protected Long updateUser;

    /** 创建时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @ExcelPOI(name = "创建时间")
    protected LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    @ExcelPOI(name = "修改时间")
    protected LocalDateTime updateTime;

    /** 备注 */
    @TableField(value = "remark")
    protected String remark;

    @ApiModelProperty(name = "sort" , value = "排序")
    @TableField(value = "sort")
    private Integer sort;

}
