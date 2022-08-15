package com.flybirds.common.enums.user;

import cn.hutool.core.util.ArrayUtil;
import com.flybirds.common.util.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户类型枚举
 *
 * @author flybirds
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum implements IntArrayValuable  {

    MEMBER(0, "普通用户"), // 面向 c 端，普通用户
    ADMIN(1, "管理员"); // 面向 b 端，管理后台

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserTypeEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String label;

    public static UserTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value), UserTypeEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }


}
