package com.flybirds.web.web.core.util.user;

import java.util.Objects;

/**
 * 用户ID获取工具类
 *
 * @author flybirds
 */
public class UserIdUtil {

    public static final Long ZERO_LONG = 0L;

    public static final String USER_NAME = "未知用户";

    private static ThreadLocal<Long> threadLocalId = new ThreadLocal<>();

    private static ThreadLocal<String> threadLocalName = new ThreadLocal<>();


    /**
     * 设置当前登录的用户ID
     *
     * @param value
     */
    public static void setId(Long value) {
        threadLocalId.set(value);
    }

    /**
     * 设置当前登录的用户ID
     *
     * @param userName
     */
    public static void setUserName(String userName) {
        threadLocalName.set(userName);
    }

    /**
     * 获取当前登录的用户ID
     *
     * @return
     */
    public static Long get() {
        Long value = threadLocalId.get();
        if (Objects.isNull(value)) {
            return ZERO_LONG;
        }
        return value;
    }

    /**
     * 获取当前登录的用户ID
     *
     * @return
     */
    public static String getUserName() {
        String value = threadLocalName.get();
        if (Objects.isNull(value)) {
            return USER_NAME;
        }
        return value;
    }

}
