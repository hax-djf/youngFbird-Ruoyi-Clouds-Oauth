package com.flybirds.api.core.dto;

import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :flybirds
 *
 * 账号日志操作记录
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogininforRespDTO implements Serializable{

    @ApiModelProperty(value = "用户登录浏览器记录")
    private String userAgent;

    private String userIp;

    private String username;

    @ApiModelProperty(value = "状态 0 成功 1 失败")
    private String status;

    private String message;

    @ApiModelProperty(value = "账号操作类型 0 登录 1 退出")
    private String type;

    @ApiModelProperty(value = "账号登录渠道")
    private AccountLoginChannelEnum accountLoginChannelEnum;


}
