package com.flybirds.common.core.model;

import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户登录对象
 *  @author flybirds
 * @Data : 使用这个注解，就不用再去手写Getter,Setter,equals,canEqual,hasCode,toString等方法了，注解后在编译时会自动加进去。
 * @EqualsAndHashCode(callSuper = false)使用@Data时同时加上@EqualsAndHashCode(callSuper=true)注解,可通过callSuper=true解决上一点问题。让其生成的方法中调用父类的方法
 * @Accessors(chain = true)  注解用来配置lombok如何产生和显示getters和setters的方法。chain 一个布尔值。如果为真，产生的setter返回的this而不是void。默认是假。如果fluent=true，那么chain默认为真
 * @AllArgsContructor ： 会生成一个包含所有变量
 * @NoArgsConstructor : 生成一个无参数的构造方法
 *
 * 配置文件已经成功全局配置如下 =>root 下 lombok.config
 *  config.stopBubbling = true
 *  lombok.tostring.callsuper=true
 *  lombok.equalsandhashcode.callsuper=true
 *  lombok.accessors.chain=true
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqVo0 implements Serializable {

    @ApiModelProperty(value = "授权模式")
    private String grantType;

    @ApiModelProperty(value = "客户端id")
    private String clientId;

    @ApiModelProperty(value = "客户端秘钥")
    private String clientSecret;

    /** {@link AccountLoginChannelEnum#getValue()} */
    @ApiModelProperty(value = "用户登录渠道")
    private Integer loginChannel;

}
