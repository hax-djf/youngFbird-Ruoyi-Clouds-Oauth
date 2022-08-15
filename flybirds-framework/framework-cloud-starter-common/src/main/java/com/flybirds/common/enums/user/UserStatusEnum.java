package com.flybirds.common.enums.user;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 * 
 * @author flybirds
 */
@AllArgsConstructor
@Getter
public enum UserStatusEnum {
    OK("0", "正常"), ERROR("1","异常"), DISABLE("1", "停用"), DELETED("1", "删除"),LOCKS("1","锁定");

    //编码
    private final String code;

    //名称
    private final String info;

    public boolean isTrue(String code){
       return ObjectUtil.equal(this.code,code);
    }

    public boolean isTrue(Boolean b){
        return ObjectUtil.equal(this.code,this.getCode());
    }
}
