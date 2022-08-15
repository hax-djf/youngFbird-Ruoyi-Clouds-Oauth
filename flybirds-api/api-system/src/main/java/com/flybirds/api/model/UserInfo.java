package com.flybirds.api.model;

import com.flybirds.api.core.entity.SysUser;

import java.util.Set;

/**
 * 用户的全部信息【基本信息 + 权限 + 角色】
 *
 * @author ruoyi
 */
public class UserInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户基本信息
     */
    private SysUser sysUser;

    /**
     * 权限标识集合
     */
    private Set<String> permissions;

    /**
     * 角色集合
     */
    private Set<String> roles;


    public SysUser getSysUser()
    {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser)
    {
        this.sysUser = sysUser;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    public Set<String> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<String> roles)
    {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "sysUser=" + sysUser +
                ", permissions=" + permissions +
                ", roles=" + roles +
                '}';
    }
}
