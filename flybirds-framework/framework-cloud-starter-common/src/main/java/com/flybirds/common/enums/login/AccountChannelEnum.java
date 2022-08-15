package com.flybirds.common.enums.login;

import com.flybirds.common.handler.DisplayedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  账号注册登录渠道 => EnumOrdinalTypeHandler转换
 *
 * @author : flybirds
 **/
@AllArgsConstructor
@Getter
public enum AccountChannelEnum implements DisplayedEnum {

    WX_MP(0, "微信扫码"),
    EMAIL(1, "邮箱"),
    PHONE(2, "手机号"),
    QQ(3, "QQ"),
    CREATE(4, "用户创建")
    ;

    private Integer value;

    private String label;

}
