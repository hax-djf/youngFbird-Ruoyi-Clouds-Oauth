package com.flybirds.common.enums.login;

import com.flybirds.common.handler.DisplayedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号登录状态
 *
 * @author :flybirds
 */
@Getter
@AllArgsConstructor
public enum AccountLoginOutStatusEnum implements DisplayedEnum {


    OUT_SUCCESS(0,"退出成功"),

    OUT_FAILURE(1,"退出失败")
    ;

    private Integer value;

    private String label;
}
