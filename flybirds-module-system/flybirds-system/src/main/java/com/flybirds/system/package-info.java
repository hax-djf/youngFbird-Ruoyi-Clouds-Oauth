/**
 *  多租户关系的用户关系介绍
 *  sys_user 用户
 *  sys_role 角色 具有租户id
 *  sys_dept 部门
 *  sys_role_menu 角色菜单关系
 *  sys_user_role 用户和角色关系
 *  sys_role_dept 角色和部门关系
 *  sys_post 岗位
 *  sys_user_post 用户和岗位关系
 *
 *  用户----角色 多对多
 *  用户----岗位 多对多
 *  通过角色关联到对应的部门 多对多
 *  通过角色关联到对应的菜单 多对多
 *  通过用户关联到对用岗位  多对多
 *
 *  在租户对用关系下的话 所有的权限表，都需要加上租户tenant_id的字段
 *  未加上 日志记录表 、sys_menu 菜单、租户、错误码
 *
 */
package com.flybirds.system;
