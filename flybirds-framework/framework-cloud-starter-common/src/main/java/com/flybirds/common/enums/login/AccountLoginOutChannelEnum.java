package com.flybirds.common.enums.login;

import com.flybirds.common.handler.DisplayedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号退出渠道方式
 *
 * @author :flybirds
 */
@AllArgsConstructor
@Getter
public enum AccountLoginOutChannelEnum implements DisplayedEnum{


    /**
     * 管理员退出
     */
    ADMIN_EXIT(2,"管理员强制退出","AdminExitService0"),

    /**
     * 强制挤出
     */
    USER_FORCEDOUT(3,"强制挤出","UserForceDoutService0"),
    /**
     * 注销
     */
    LOGIN_OUT(4,"用户注销","LoginOutService0"),
    /*
      * 清除
     */
    LOGIN_CLEAN(5,"用户清除","LoginCleanService0")

    ;

    private Integer value;

    private String label;

    private String className;

}
