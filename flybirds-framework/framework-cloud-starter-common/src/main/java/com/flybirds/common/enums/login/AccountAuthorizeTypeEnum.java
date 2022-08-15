package com.flybirds.common.enums.login;

import com.flybirds.common.handler.DisplayedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户授权认证平台类型
 *
 * @author : flybirds
 **/
@AllArgsConstructor
@Getter
public enum AccountAuthorizeTypeEnum implements DisplayedEnum{

    QQ(0, "QQ"),

    GIT_EE(0, "gitee"),

    GIT_HUB(0, "github");


    private Integer value;

    private String label;


//    @JsonValue
//    private int value;
//
//    private String label;
//
//    /**
//     * 枚举入参注解
//     *
//     * @param value =>mybats_pluse 支持
//     * @return
//     */
//    @JsonCreator
//    public static UserAuthorizeTypeEnum getByValue(int value) {
//        for (UserAuthorizeTypeEnum item : values()) {
//            if (item.getValue() == value) {
//                return item;
//            }
//        }
//        return null;
//    }
}
