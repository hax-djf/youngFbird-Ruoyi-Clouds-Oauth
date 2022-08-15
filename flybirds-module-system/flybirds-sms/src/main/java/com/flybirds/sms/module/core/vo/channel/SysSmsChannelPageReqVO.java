package com.flybirds.sms.module.core.vo.channel;

import com.flybirds.mybatis.core.condition.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

@ApiModel("短信渠道分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysSmsChannelPageReqVO extends PageParam {

    @ApiModelProperty(value = "任务状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "短信签名", example = "芋道源码", notes = "模糊匹配")
    private String signature;

    @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
