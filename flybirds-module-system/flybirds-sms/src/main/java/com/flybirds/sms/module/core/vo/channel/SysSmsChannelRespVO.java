package com.flybirds.sms.module.core.vo.channel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;
import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

@ApiModel("短信渠道 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysSmsChannelRespVO extends SysSmsChannelBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "1024")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "渠道编码", required = true, example = "YUN_PIAN", notes = "参见 SmsChannelEnum 枚举类")
    private String code;

    @ApiModelProperty(value = "创建时间", required = true)
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    private Date createTime;

}
