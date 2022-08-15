package com.flybirds.system.sysUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flybirds.api.core.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author ruoyi
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
     List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
     SysUser selectUserByUserName(String userName);

    /**
     * 通过用户名查询用户
     *
     * @param Phone 用户名
     * @return 用户对象信息
     */
     SysUser selectUserByPhone(String Phone);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
     SysUser selectUserById(Long userId);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
     SysUser selectUserDetailById(Long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
     int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
     int updateUser(SysUser user);

    /**
     * 用户登录之后状态更新
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateSysUserLoginByName(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
     int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
     int resetUserPwdByName(@Param("userName") String userName, @Param("passWord") String password);

    /**
     * 重置用户密码
     *
     * @param userId 用户id
     * @param password 密码
     * @return 结果
     */
     int resetUserPwdById(@Param("userId") Long userId, @Param("passWord") String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
     int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
     int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
     int checkUserNameUnique(String userName);

    /**
     * email获取用户信息
     * @param email
     * @return
     */
     SysUser selectUserByEmail(@Param("email") String email);

    /**
     * 根据岗位和部门获取用户名
     * @param postCode
     * @param deptId
     * @return
     */
    List<String> selectUserNameByPostCodeAndDeptId(String postCode, Long deptId);

    /**
     * 根据条件分页查询未已配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
     List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
     List<SysUser> selectUnallocatedList(SysUser user);
}
