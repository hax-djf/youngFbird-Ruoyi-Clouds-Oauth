package com.flybirds.sms.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flybirds.mybatis.core.basepojo.MpBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * @author :flybirds
 * @create :2021-06-30 20:45:00
 * @description :
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_sms_channel",autoResultMap = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysSmsChannel extends MpBaseEntity {

    @ApiModelProperty(name = "id" , value = "主键")
    @TableId(value = "id",type= IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "短信 API 的账号", required = true, example = "apiKey")
    @NotNull(message = "短信 API 的账号不能为空")
    @TableField(value = "api_key")
    private String apiKey;

    @ApiModelProperty(value = "短信 API 的秘钥", example = "apiSecret")
    @TableField(value = "api_secret")
    private String apiSecret;

    @ApiModelProperty(value = "短信签名", required = true, example = "短信签名")
    @NotNull(message = "短信签名不能为空")
    @TableField(value = "signature")
    private String signature;

    @ApiModelProperty(value = "启用状态", required = true, example = "1")
    @NotNull(message = "启用状态不能为空")
    @TableField(value = "status")
    private Integer status;

    /**
     * {@link com.flybirds.smsprovider.client.enums.SmsChannelEnum}
     */
    @ApiModelProperty(value = "渠道编码", required = true, example = "ALI_YUN", notes = "参见 SmsChannelEnum 枚举类")
    @NotNull(message = "渠道编码不能为空")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "短信发送回调 URL", example = "回调地址")
    @URL(message = "回调 URL 格式不正确")
    @TableField(value = "callback_url")
    private String callbackUrl;

}
