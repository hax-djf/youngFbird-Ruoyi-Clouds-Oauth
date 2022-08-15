package com.flybirds.system.sysUser.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.flybirds.api.core.entity.SysRole;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.util.collection.CollectionUtils;
import com.flybirds.system.sysMenu.entity.SysMenu;
import com.flybirds.system.sysMenu.service.ISysMenuService;
import com.flybirds.system.sysRole.entity.SysRoleMenu;
import com.flybirds.system.sysRole.mapper.SysRoleMenuMapper;
import com.flybirds.system.sysRole.service.ISysRoleService;
import com.flybirds.system.sysUser.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService
{
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    /**
     * 获取角色数据权限
     * 
     * @param userId 用户Id
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(Long userId)
    {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (SysUser.isAdmin(userId))
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(roleService.selectRolePermissionByUserId(userId));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     * 
     * @param userId 用户Id
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(Long userId)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (SysUser.isAdmin(userId))
        {
            perms.add("*:*:*");
        }
        else
        {
            perms.addAll(menuService.selectMenuPermsByUserId(userId));
        }
        return perms;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有菜单编号
        Set<Long> dbMenuIds = CollectionUtils.convertSet(roleMenuMapper.selectListByRoleId(roleId),
                SysRoleMenu::getMenuId);
        // 计算新增和删除的菜单编号
        Collection<Long> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createMenuIds)) {
            roleMenuMapper.insertList(roleId, createMenuIds);
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            roleMenuMapper.deleteListByRoleIdAndMenuIds(roleId, deleteMenuIds);
        }
    }

    @Override
    public Set<Long> getRoleMenuIds(Long roleId) {
        // 如果是管理员的情况下，获取全部菜单编号
        SysRole role = roleService.selectRoleById(roleId);
        if (roleService.hasAnySuperAdmin(Collections.singletonList(role))) {
            return CollectionUtils.convertSet(menuService.getMenus(), SysMenu::getMenuId);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        return CollectionUtils.convertSet(roleMenuMapper.selectListByRoleId(roleId),
                SysRoleMenu::getMenuId);
    }
}
