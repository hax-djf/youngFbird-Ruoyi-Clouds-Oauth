package com.flybirds.sms.module.core.vo.template;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flybirds.common.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;

@ApiModel("短信模板 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysSmsTemplateRespVO extends SysSmsTemplateBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "1024")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "短信渠道编码", required = true, example = "ALIYUN")
    private String channelCode;

    @ApiModelProperty(value = "参数数组", example = "name,code")
    private List<String> params;

    @ApiModelProperty(value = "创建时间", required = true)
    @JsonFormat(pattern = Constant.DateFormat.YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    private Date createTime;

}
