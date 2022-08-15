package com.flybirds.system.sysUser.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 当前在线会话
 * 
 * @author ruoyi
 */
@Data
public class SysUserOnline implements Serializable
{
    /** token加密密钥盐值 */
    private String tokenSecretkey;

    /** 用户名称 */
    private String userName;

    /** 用户id */
    private long userId;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地址 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 登录时间 */
    private Long loginTime;

    /** 登录的设备端 */
    private String scope;

    /** 过期时间 */
    private long expiresIn;

    //根据一下参数去重
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysUserOnline)) return false;
        SysUserOnline that = (SysUserOnline) o;

        if (userId != that.userId) return false;
        if (tokenSecretkey != null ? !tokenSecretkey.equals(that.tokenSecretkey) : that.tokenSecretkey != null)
            return false;
        return userName != null ? userName.equals(that.userName) : that.userName == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (tokenSecretkey != null ? tokenSecretkey.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }
}
