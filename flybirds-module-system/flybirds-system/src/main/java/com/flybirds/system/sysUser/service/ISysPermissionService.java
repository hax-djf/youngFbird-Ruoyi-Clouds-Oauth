package com.flybirds.system.sysUser.service;

import java.util.Set;

public interface ISysPermissionService
{
    /**
     * 获取角色数据权限
     * 
     * @param userId 用户Id
     * @return 角色权限信息
     */
     Set<String> getRolePermission(Long userId);

    /**
     * 获取菜单数据权限
     * 
     * @param userId 用户Id
     * @return 菜单权限信息
     */
     Set<String> getMenuPermission(Long userId);

    /**
     * 修改角色的菜单权限
     *
     * @param roleId
     * @param menuIds
     */
     void assignRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * 获取角色的菜单权限
     * @param roleId
     * @return
     */
     Set<Long> getRoleMenuIds(Long roleId);
}
