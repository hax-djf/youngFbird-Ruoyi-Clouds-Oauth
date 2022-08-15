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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;
import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;

/**
 * @author :flybirfd
 * @create :2021-06-30 21:05:00
 * @description : 短信发送日志类
 * <p>
 * 注意：lombok使用 @builder的时候出初始化构造一个 有参构造方法，如果继承了父类，需要手动进行super()分类的参数 ，否则出现在反射异常
 */
@Data
@TableName(value = "sys_sms_log", autoResultMap = true)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysSmsLog implements Serializable {

    @ApiModelProperty(value = "编号", example = "10")
    @ExcelPOI(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 关联关系 {@link SysSmsChannel#getId()}
     */
    @ExcelPOI(name = "短信渠道编号")
    private Long channelId;

    @ExcelPOI(name = "短信渠道编码")
    private String channelCode;

    @ExcelPOI(name = "模板编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long templateId;

    @ExcelPOI(name = "模板编码")
    private String templateCode;

    @ExcelPOI(name = "短信类型")
    private Integer templateType;

    @ExcelPOI(name = "短信内容")
    private String templateContent;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;

    @ExcelPOI(name = "短信 API 的模板编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private String apiTemplateId;

    @ExcelPOI(name = "手机号")
    private String mobile;

    @ExcelPOI(name = "用户编号")
    private Long userId;

    @ExcelPOI(name = "用户类型")
    private Integer userType;

    @ExcelPOI(name = "发送状态")
    private Integer sendStatus;

    @ExcelPOI(name = "发送时间")
    private Date sendTime;

    @ExcelPOI(name = "发送结果的编码")
    private Integer sendCode;

    @ExcelPOI(name = "发送结果的提示")
    private String sendMsg;

    @ExcelPOI(name = "短信 API 发送结果的编码")
    private String apiSendCode;

    @ExcelPOI(name = "短信 API 发送失败的提示")
    private String apiSendMsg;

    @ExcelPOI(name = "短信 API 发送返回的唯一请求 ID")
    private String apiRequestId;

    @ExcelPOI(name = "短信 API 发送返回的序号")
    private String apiSerialNo;

    @ExcelPOI(name = "接收状态")
    private Integer receiveStatus;

    @ExcelPOI(name = "接收时间")
    private Date receiveTime;

    @ExcelPOI(name = "API 接收结果的编码")
    private String apiReceiveCode;

    @ExcelPOI(name = "API 接收结果的说明")
    private String apiReceiveMsg;

    /**
     * 创建者账号
     */
    @TableField(value = "create_name", fill = FieldFill.INSERT)
    private String createName;

    /**
     * 创建者id
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS, timezone = TIME_ZONE_DEFAULT_GMT)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ExcelPOI(name = "创建时间")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty(name = "sort", value = "排序")
    @TableField(value = "sort")
    private Integer sort;


}
