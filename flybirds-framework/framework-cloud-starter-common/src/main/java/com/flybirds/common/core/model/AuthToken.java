package com.flybirds.common.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 自定义认证AuthToken数据
 *
 * @author flybirds
 */
@Data
@Builder
@NoArgsConstructor  //给一个无参构造 用于处理 utoType is not support. com.flybirds.common.utils.oauth.AuthToken问题
@AllArgsConstructor
public class AuthToken implements Serializable{

    @ApiModelProperty(name = "accessToken",value = "token")
    private String accessToken;

    @ApiModelProperty(name = "refreshToken",value = "token的刷新值")
    private  String refreshToken;

    @ApiModelProperty(name = "jti",value = "jti")
    private  String jti;

    @ApiModelProperty(name = "scope",value = "scope")
    private String scope;

    @ApiModelProperty(name = "expires_in",value = "expires_in")
    private Integer expires_in;

    @ApiModelProperty(name = "token_type",value = "token_type")
    private String token_type;

    @ApiModelProperty(name = "user_id",value = "user_id")
    private Long user_id;

    @ApiModelProperty(name = "user_name",value = "user_name")
    private String user_name;

    @ApiModelProperty(name = "mobile",value = "mobile")
    private  String mobile;

    @ApiModelProperty(name = "online_number",value = "online_number")
    private  Integer online_number;

    @ApiModelProperty(name = "oauth_token_secretkey",value = "oauth_token_secretkey")
    private String oauth_token_secretkey;

    @ApiModelProperty(name = "user_agent",value = "user_agent")
    private  String  user_agent;

    @ApiModelProperty(name = "user_ip",value = "user_ip")
    private  String  user_ip;

    @ApiModelProperty(name = "tenant_id",value = "tenant_id")
    private Long tenant_id;

    /* 返回目标数据 */
    public AuthToken Target(){
       return AuthToken.builder()
            .accessToken(accessToken)
            .mobile(mobile)
            .user_id(user_id)
            .user_name(user_name)
            .token_type(token_type)
            .expires_in(expires_in)
            .tenant_id(tenant_id)
            .user_agent(user_agent)
            .user_ip(user_ip)
            .online_number(online_number)
            .scope(scope).build();
    }
}