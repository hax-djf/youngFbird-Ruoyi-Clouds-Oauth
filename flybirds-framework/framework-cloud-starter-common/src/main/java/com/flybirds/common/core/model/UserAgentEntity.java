package com.flybirds.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserAgent 用户登录来源信息
 *
 * @author :flybirds
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAgentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;
}
