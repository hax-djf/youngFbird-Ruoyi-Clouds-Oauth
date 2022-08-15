package com.flybirds.common.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author :flybirds
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountLoginReqVo0 extends LoginReqVo0 {


    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "唯一标识 uuid")
    private String uuid = "";
}
