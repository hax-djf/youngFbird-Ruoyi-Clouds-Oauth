package com.flybirds.common.enums.login;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 账号的授权范围
 *
 * @author :flybirds
 */
@AllArgsConstructor
@Getter
public enum AccountAuthorizationScopeEnum {

    USER_INFO("user_info","访问用户的个人基本信息等"),
    EMAILS("emails","查看用户的个人基本邮箱信息"),
    DYNAMIC("dynamic","访问用户动态数据");

    private String value;

    private String label;

    public static AccountAuthorizationScopeEnum get(String label){
        AccountAuthorizationScopeEnum channel = null;
        for(AccountAuthorizationScopeEnum scopeEnum : AccountAuthorizationScopeEnum.values()){
            if(ObjectUtil.equal(scopeEnum.getLabel(),label)){
                channel=  scopeEnum;
                break;
            }
        }
        return channel;
    }

    public static List<AccountAuthorizationScopeEnum> get(Set<String> set){
        return Arrays.asList(AccountAuthorizationScopeEnum.values())
                .stream().filter(item->set.contains(item.getValue().trim())).collect(Collectors.toList());

    }
}
