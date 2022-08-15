package com.flybirds.system.sysRole.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author flybirds
 */
@TableName("sys_role_menu")
@Data
public class SysRoleMenu
{
    @TableId(type = IdType.AUTO)
    private String id;

    /** 角色ID */
    private Long roleId;
    
    /** 菜单ID */
    private Long menuId;

    /*租户id*/
    private Long tenantId;
}
