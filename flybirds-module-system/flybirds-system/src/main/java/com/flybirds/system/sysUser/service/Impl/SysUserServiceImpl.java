package com.flybirds.system.sysUser.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flybirds.api.RemoteFileService;
import com.flybirds.api.core.entity.SysFileInfo;
import com.flybirds.api.core.entity.SysRole;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.enums.CommonStatusEnum;
import com.flybirds.common.enums.login.AccountChannelEnum;
import com.flybirds.common.exception.CustomException;
import com.flybirds.common.util.collection.CollectionUtils;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.datascope.annotation.DataScope;
import com.flybirds.redis.manger.RedisCacheManger;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.system.sysConfig.service.ISysConfigService;
import com.flybirds.system.sysDept.service.ISysDeptService;
import com.flybirds.system.sysPost.entity.SysPost;
import com.flybirds.system.sysPost.mapper.SysPostMapper;
import com.flybirds.system.sysPost.service.ISysPostService;
import com.flybirds.system.sysRole.entity.SysUserRole;
import com.flybirds.system.sysRole.mapper.SysRoleMapper;
import com.flybirds.system.sysUser.convert.UserConvert;
import com.flybirds.system.sysUser.entity.SysUserPost;
import com.flybirds.system.sysUser.mapper.SysUserMapper;
import com.flybirds.system.sysUser.mapper.SysUserPostMapper;
import com.flybirds.system.sysUser.mapper.SysUserRoleMapper;
import com.flybirds.system.sysUser.service.ISysPermissionService;
import com.flybirds.system.sysUser.service.ISysUserService;
import com.flybirds.system.sysUser.vo.RegisterAccountRequest;
import com.flybirds.system.sysUser.vo.UserCreateReqVO;
import com.flybirds.system.utils.NameUtils;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.flybirds.common.enums.user.UserTypeEnum.MEMBER;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.*;
import static com.flybirds.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 用户 业务层处理
 * 
 * @author ruoyi
 */
@Service
@SuppressWarnings("all")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements ISysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private RemoteFileService remoteFileService;

    @Autowired
    private RedisCacheManger redisCacheManger;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysPostService postService;
    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return makeUpRoldsAndRemoveEntity(userMapper.selectUserByUserName(userName));
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     * 
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     * 
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName)
    {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return Constant.User.NOT_UNIQUE;
        }
        return Constant.User.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.selectUserByPhone(user.getPhonenumber());
        if (ObjectUtil.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return Constant.User.NOT_UNIQUE;
        }
        return Constant.User.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.selectUserByEmail(user.getEmail());
        if (ObjectUtil.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return Constant.User.NOT_UNIQUE;
        }
        return Constant.User.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user)
    {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
//    @GlobalTransactional // 重点 第一个开启事务的需要添加seata全局事务注解
    public int updateUser(SysUser user) {
        /**
         * 测试将图片信息存入到数据库中
         */
        if(StringUtils.isNotNull(user.getAvatar())){
            SysFileInfo sysFileInfo =new SysFileInfo();
            sysFileInfo.setFileName("sysFileInfo用户图");
            sysFileInfo.setFilePath(user.getAvatar());
            remoteFileService.saveFile(sysFileInfo);
        }

        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     * 
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwdByName(String userName, String password)
    {
        return userMapper.resetUserPwdByName(userName, password);
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotNull(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     * 
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(SysUser.builder().userId(userId).build());
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u))
                {
                    user.setPassWord(SecurityUtils.encryptPassword(password));
                    user.setCreateName(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    user.setUpdateName(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public SysUser selectUserByPhone(String phone) {

        return makeUpRoldsAndRemoveEntity(userMapper.selectUserByPhone(phone));
    }

    @Override
    public SysUser selectUserByUserEmail(String email) {

        return makeUpRoldsAndRemoveEntity(userMapper.selectUserByEmail(email));

    }

    /**
     * user用户信息组装
     * @param sysUser
     * @return
     */
    @Override
    public UserInfo makeUpUserInfo(SysUser sysUser){
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser.getUserId());
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser.getUserId());
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        userInfo.setRoles(roles);
        userInfo.setPermissions(permissions);
        return userInfo;
    }

    @Override
    public Result<String> checkPhoneValid(String mobile) {
        Result<String> result = Result.ok();
        SysUser user = userMapper.selectUserByPhone(mobile);
        if(ObjectUtil.isNotEmpty(user)){
            boolean enabled = "0".equals(user.getStatus());
            boolean accountNonExpired  = user.getDelFlag(); //是否删除
            boolean accountNonLocked ="0".equals(user.getLocks());

            StringBuilder mesage = new StringBuilder();
            if(!enabled){
                mesage.append("账号停用！");
            }
            if(accountNonExpired){
                mesage.append("账号已注销！");
            }
            if(!accountNonLocked){
                mesage.append("账号已锁定！");
            }

            if(mesage.length()>0){
                result.setCode(CodeConstant.Number.FAILURE);
                result.setMsg(mesage.toString());
            }
            result.setData(user.getUserName());
        }else{
            result.setCode(CodeConstant.Number.FAILURE);
            result.setMsg("手机号未注册！");
        }
        return result;
    }

    /**
     * 邮箱创建用户
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result emailRegister(RegisterAccountRequest request) {
        //检查验证码是否正确
        String code = redisCacheManger.getCacheObject(StrUtil.format(CacheConstantEnum.EMAIL_CODE.getKey(), request.getEmail()), String.class);
        if (ObjectUtil.notEqual(request.getCode(),code)) {
            return Result.fail("验证码错误");
        }
        if (ObjectUtil.isNotNull(selectUserByUserEmail(request.getEmail()))) {
            return Result.fail("该邮箱已经注册");
        }
        SysUser userEntity = new SysUser();
        userEntity.setEmail(request.getEmail());
        userEntity.setPassWord(request.getPassword());
        userEntity.setRegChannel(AccountChannelEnum.EMAIL);
        userEntity.setCreateName(Constant.Register.CRRATE_BY_EMEAIL);
        userEntity.setCreateUser(Constant.Register.USER_ADMIN_ID);
        return createUser(userEntity) > 0 ? Result.ok("注册成功") : Result.fail("注册失败");
    }

    /**
     * 手机创建用户
     * @param request
     * @return
     */
    @Override
    public Result phoneRegister(RegisterAccountRequest request) {

        if (ObjectUtil.isNotNull(selectUserByPhone(request.getPhoneNumber()))) {
            return Result.fail("该手机号已经注册");
        }
        SysUser userEntity = new SysUser();
        userEntity.setPhonenumber(request.getPhoneNumber());
        userEntity.setPassWord(request.getPassword());
        userEntity.setRegChannel(AccountChannelEnum.EMAIL);
        userEntity.setCreateName(Constant.Register.CRRATE_BY_PHONE);
        userEntity.setCreateUser(Constant.Register.USER_ADMIN_ID);
        return createUser(userEntity) > 0 ? Result.ok("注册成功") : Result.fail("注册失败");
    }

    /**
     * 修改密码
     * @param userId
     * @param password
     */
    @Override
    public int resetUserPwdById(Long userId, @NotBlank(message = "密码不能为空") String password) {
        return userMapper.resetUserPwdById(userId,password);
    }

    /**
     * common 注册用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    protected int createUser(SysUser userEntity){
        //分配一个默认账号信息
        userEntity.setNickName(NameUtils.getCnName());
        userEntity.setAvatar(Constant.Register.DEFAULT_AVATAR);
        userEntity.setPassWord(SecurityUtils.encryptPassword(userEntity.getPassWord()));
        //设置默认权限信息权限
        userEntity.setDeptId(Constant.Register.DEFAULT_DEPT_ID);
        userEntity.setPostIds(Constant.Register.DEFAULT_POST_ID);
        userEntity.setRoleIds(Constant.Register.DEFAULT_ROLE_ID);
        userEntity.setCreateUser(Constant.Register.USER_ADMIN_ID);
        userEntity.setStatus(Constant.Register.DEFAULT_STATUS);
        userEntity.setLocks(Constant.Register.DEFAULT_LOCKS);
        userEntity.builderInsertBaseDo(null,null);
        //普通用户
        userEntity.setUserType(MEMBER);
        // todo 生产默认账号->后期开发
        userEntity.setUserName(RandomStringUtils.randomAlphabetic(8));
        //存储
       return insertUser(userEntity);
    }
    /**
     * 用户权限数据封装
     * @param sysUser
     * @return
     */
    private SysUser makeUpRoldsAndRemoveEntity(SysUser sysUser){

        if(StringUtils.isNull(sysUser)){
            log.error("用户数据找不到");
        }else{
            if(StringUtils.isNotNull(sysUser.getRoles())){

                List<Long>  roles = sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList());

                Long[] array = new Long[roles.size()];

                sysUser.setRoleIds(roles.toArray(array));
            }
        }
        return sysUser;
    }

    /**
     *
     * @param postCode
     * @param deptId
     * @return
     */
    public List<String>selectUserNameByPostCodeAndDeptId(String postCode,Long deptId){
        return userMapper.selectUserNameByPostCodeAndDeptId(postCode,deptId);
    }


    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    @Override
    public SysUser selectUserDetailById(Long userId) {
        return  userMapper.selectUserDetailById(userId);
    }

    @Override
    public void updateSysUserLoginByName(SysUser sysUser) {
        userMapper.updateSysUserLoginByName(sysUser);
    }

    /* 租户用户创建 */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateReqVO reqVO) {
        // 校验正确性
        this.checkCreateOrUpdate(null,reqVO.getUserName(), reqVO.getPhonenumber(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // 插入用户
        SysUser user = UserConvert.INSTANCE.convert(reqVO);
        user.setStatus(String.valueOf(CommonStatusEnum.ENABLE.getStatus())); // 默认开启
        user.setPassWord(SecurityUtils.encryptPassword(reqVO.getPassWord())); // 加密密码
        //插入用户 ->包含了部门和岗位，角色所有的信息
        insertUser(user);
        return user.getUserId();
    }

    private void checkCreateOrUpdate(Long id,String username, String mobile, String email,
                                     Long deptId, Long[] postIds) {
        // 校验用户名唯一
        this.checkUserNameUnique(id,username);
        // 校验手机号唯一
        this.checkPhoneUnique(id,mobile);
        // 校验邮箱唯一
        this.checkEmailUnique(id,email);
        // 校验部门处于开启状态
        deptService.validDepts(CollectionUtils.singleton(deptId));
        // 校验岗位处于开启状态
        postService.validPosts(postIds);
    }

    @VisibleForTesting
    private void checkPhoneUnique(Long id,String phone)
    {
        Long userId = StringUtils.isNull(id) ? -1L : id;
        SysUser info = userMapper.selectUserByPhone(phone);
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    @VisibleForTesting
    private void checkEmailUnique(Long id,String email)
    {
        Long userId = StringUtils.isNull(id) ? -1L : id;
        SysUser info = userMapper.selectUserByEmail(email);
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    @VisibleForTesting
    private void checkUserNameUnique(Long id,String userName)
    {
        Long userId = StringUtils.isNull(id) ? -1L : id;
        SysUser info = userMapper.selectUserByUserName(userName);
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            throw exception(USER_USERNAME_EXISTS);
        }
    }
}
