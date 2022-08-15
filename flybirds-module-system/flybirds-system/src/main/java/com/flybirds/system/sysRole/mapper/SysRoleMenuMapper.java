package com.flybirds.system.sysRole.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flybirds.mybatis.core.basemapper.MpBaseMapper;
import com.flybirds.system.sysRole.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色与菜单关联表 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysRoleMenuMapper extends MpBaseMapper<SysRoleMenu>
{
    /**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
     int checkMenuExistRole(Long menuId);

    /**
     * 通过角色ID删除角色和菜单关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
     int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 批量删除角色菜单关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteRoleMenu(Long[] ids);

    /**
     * 批量新增角色菜单信息
     * 
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
     int batchRoleMenu(List<SysRoleMenu> roleMenuList);

    default List<SysRoleMenu> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
    }

    default void insertList(Long roleId, Collection<Long> menuIds) {
        List<SysRoleMenu> list = menuIds.stream().map(menuId -> {
            SysRoleMenu entity = new SysRoleMenu();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            return entity;
        }).collect(Collectors.toList());
        insertBatch(list);
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId)
                .in("menu_id", menuIds));
    }
}
