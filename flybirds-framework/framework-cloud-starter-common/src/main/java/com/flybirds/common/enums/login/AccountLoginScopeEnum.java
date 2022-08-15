package com.flybirds.common.enums.login;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.common.handler.DisplayedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号登录终端设备
 *
 * @author :flybirds
 */
@AllArgsConstructor
@Getter
public enum AccountLoginScopeEnum implements DisplayedEnum {

    /**
     * 短信登录
     */
    PC(0,"pc","WebLoginScopeService0"),
    /**
     * app 扫码
     */
    APP(1,"app","AppLoginScopeService0"),
    /**
     * 微信小程序
     */
    MINI_WECHAT_APP(2,"mini_wechat_app","AppLoginScopeService0");

    private Integer value;

    private String label;

    private String ClassName;

    public static AccountLoginScopeEnum get(String label){
        AccountLoginScopeEnum channel = null;
        for(AccountLoginScopeEnum scopeEnum : AccountLoginScopeEnum.values()){
            if(ObjectUtil.equal(scopeEnum.getLabel(),label)){
                channel=  scopeEnum;
                break;
            }
        }
        return channel;
    }
}
