package com.flybirds.sms.module.core.vo.template;

import com.flybirds.mybatis.core.condition.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;


@ApiModel("短信模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysSmsTemplatePageReqVO extends PageParam {

    @ApiModelProperty(value = "短信签名", example = "1")
    private Integer type;

    @ApiModelProperty(value = "开启状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "模板编码", example = "test_01", notes = "模糊匹配")
    private String code;

    @ApiModelProperty(value = "模板内容", example = "你好，{name}。你长的太{like}啦！", notes = "模糊匹配")
    private String content;

    @ApiModelProperty(value = "短信 API 的模板编号", example = "4383920", notes = "模糊匹配")
    private String apiTemplateId;

    @ApiModelProperty(value = "短信渠道编号", example = "10")
    private Long channelId;

    @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
