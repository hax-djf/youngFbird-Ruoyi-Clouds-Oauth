package com.flybirds.system.sysUser.controller;

import com.flybirds.api.core.entity.SysRole;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.exception.UserException;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.system.sysPost.service.ISysPostService;
import com.flybirds.system.sysRole.service.ISysRoleService;
import com.flybirds.system.sysUser.service.ISysPermissionService;
import com.flybirds.system.sysUser.service.ISysUserService;
import com.flybirds.web.web.annotation.OauthServiceSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.flybirds.common.constant.MsgConstant.Business.NOT_FOUND_PARAMS;
import static com.flybirds.common.enums.login.AccountChannelEnum.CREATE;
import static com.flybirds.common.enums.user.UserTypeEnum.MEMBER;
import static com.flybirds.common.util.result.AjaxResult.success;

/**
 * 用户信息
 * 
 * @author flybirds
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends MpBaseController<SysUser> {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysPermissionService permissionService;


    @PreAuthorize(hasPermi = "system:user:list")
    @GetMapping("/list")
    public MpTableDataInfo list(SysUser user)
    {
        mpStartPage();
        List<SysUser> list = userService.selectUserList(user);
        return getMpDataTable(list);
    }

    @Log(title = "用户导出", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "system:user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) throws IOException
    {
        List<SysUser> list = userService.selectUserList(user);
        POIExcelUtil<SysUser> util = new POIExcelUtil<>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户导入", businessType = BusinessType.IMPORT)
    @PreAuthorize(hasPermi = "system:user:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        POIExcelUtil<SysUser> util = new POIExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        POIExcelUtil<SysUser> util = new POIExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户名获取用户的全部信息 [不需要token数据 => 需要服务之间的签名]
     * @param userName
     * @return
     */
    @GetMapping("/open/info/userName/{userName}")
    @OauthServiceSignature
    public Result<UserInfo> getUserInfo(@PathVariable("userName") String userName) {
        SysUser sysUser = userService.selectUserByUserName(userName);
        if (StringUtils.isNull(sysUser))
        {
            return Result.fail("用户名或密码错误");
        }
        UserInfo userInfo = userService.makeUpUserInfo(sysUser);

        return StringUtils.isNotNull(userInfo) ? Result.ok(userInfo) : Result.fail("用户信息获取异常");
    }

    /**
     * 用户id获取用户的全部信息
     * @param id
     * @return
     */
    @GetMapping("/open/info/id/{id}")
    @Log(title = "用户id获取用户的全部信息", businessType = BusinessType.QUERY)
    public Result<UserInfo> getUserInfoById(@PathVariable("id") Long id) {
        SysUser sysUser = userService.selectUserById(id);
        if (StringUtils.isNull(sysUser))
        {
            return Result.fail("用户名或密码错误");
        }
        UserInfo userInfo = userService.makeUpUserInfo(sysUser);

        return StringUtils.isNotNull(userInfo) ? Result.ok(userInfo) : Result.fail("用户信息获取异常");
    }


    /**
     * 根据邮箱获取用户信息  [不需要token数据 => 需要服务之间的签名]
     *
     * @param email
     * @return
     */
    @GetMapping("/open/info/email/{email}")
    @OauthServiceSignature
    public Result<UserInfo> getUserInfoByEmail(@PathVariable("email") String email) {

        SysUser sysUser = userService.selectUserByUserEmail(email);
        if (StringUtils.isNull(sysUser))
        {
            return Result.fail("邮箱未绑定注册");
        }
        UserInfo userInfo = userService.makeUpUserInfo(sysUser);
        return StringUtils.isNotNull(userInfo)?Result.ok(userInfo):Result.fail("用户信息获取异常");
    }

    /**
     * 根据手机号获取用户全部信息 [不需要token数据 => 需要服务之间的签名]
     * @param mobile
     * @return
     */
    @GetMapping("/open/info/mobile/{mobile}")
    @OauthServiceSignature
    public Result<UserInfo> getUserInfoByMobile(@PathVariable("mobile") String mobile) {

        SysUser sysUser = userService.selectUserByPhone(mobile);

        if (StringUtils.isNull(sysUser))
        {
            return Result.fail("手机号未注册！");
        }
        UserInfo userInfo = userService.makeUpUserInfo(sysUser);
        return StringUtils.isNotNull(userInfo)?Result.ok(userInfo):Result.fail("用户信息获取异常");
    }

    /**
     * 根据手机号获取用户全部信息 [不需要token数据 => 需要服务之间的签名]
     * @param mobile
     * @return
     */
    @GetMapping("/open/info/single/mobile/{mobile}")
    @OauthServiceSignature
    public Result<SysUser> getUserByMobile(@PathVariable("mobile") String mobile) {

        SysUser sysUser = userService.selectUserByPhone(mobile);

        if (StringUtils.isNull(sysUser))
        {
            return Result.fail("手机号未注册！");
        };
        return Result.ok(sysUser);
    }

    /**
     * 根据手机号获取用户全部信息 [不需要token数据 => 需要服务之间的签名]
     * @param userName
     * @return
     */
    @GetMapping("/open/info/single/userName/{userName}")
    @OauthServiceSignature
    public Result<SysUser> getUserByuserName(@PathVariable("userName") String userName) {

        SysUser sysUser = userService.selectUserByUserName(userName);

        if (StringUtils.isNull(sysUser))
        {
            return Result.fail("用户名不存在！");
        };
        return Result.ok(sysUser);
    }

    /**
     * 判断手机号是否注册 [不需要token数据 => 需要服务之间的签名]
     * @param phone
     * @return
     */
    @GetMapping("/open/check/phoneValid/{phone}")
    @OauthServiceSignature
    public Result<String> checkPhoneValid(@PathVariable("phone") String phone) {
        AssertUtil.isNotNull(phone,NOT_FOUND_PARAMS);
        return userService.checkPhoneValid(phone);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        AjaxResult ajax = success();
        Long userId = SecurityUtils.getLoginUser().getUserId();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(userId);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(userId);

        SysUser sysUser = userService.selectUserById(userId);

        AssertUtil.isNotNull(sysUser,new UserException(CodeConstant.character.ERROR,"用户信息找不到"));

        sysUser.setPassWord(null);
        ajax.put("user",sysUser);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 根据用户编号获取详细信息
     *
     * @param userId
     * @return
     */
    @PreAuthorize(hasPermi = "system:user:query")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        AjaxResult ajax = success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PreAuthorize(hasPermi = "system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (Constant.User.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && Constant.User.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && Constant.User.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateName(SecurityUtils.getUsername());
        user.setCreateUser(SecurityUtils.getUserId());
        user.setPassWord(SecurityUtils.encryptPassword(user.getPassWord()));
        user.setRegChannel(CREATE);
        user.setUserType(MEMBER);
        user.setStatus(Constant.Register.DEFAULT_STATUS);
        user.setStatus(Constant.Register.DEFAULT_STATUS);
        user.setLocks(Constant.Register.DEFAULT_LOCKS);
        user.setAvatar(Constant.Register.DEFAULT_AVATAR);
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @PreAuthorize(hasPermi = "system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && Constant.User.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && Constant.User.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateName(SecurityUtils.getUsername());
        user.setUpdateUser(SecurityUtils.getUserId());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 注销用户
     *
     * @param userIds
     * @return
     */
    @PreAuthorize(hasPermi = "system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    @PreAuthorize(hasPermi = "system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        user.setPassWord(SecurityUtils.encryptPassword(user.getPassWord()));
        user.setUpdateName(SecurityUtils.getUsername());
        user.setUpdateUser(SecurityUtils.getUserId());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     *
     * @param user
     * @return
     */
    @PreAuthorize(hasPermi = "system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        user.setUpdateName(SecurityUtils.getUsername());
        user.setUpdateUser(SecurityUtils.getUserId());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 查询用户名根据部门id
     *
     * @param se
     * @param deptId
     * @return
     */
    @Log(title = "查询用户名根据部门id", businessType = BusinessType.QUERY)
    @GetMapping("/selectUserNameByPostCodeAndDeptId")
    public List<String> selectUserNameByPostCodeAndDeptId(@RequestParam(value = "se",required = true) String se,
                                                          @RequestParam(value = "deptId",required = true) Long deptId)
    {
        return userService.selectUserNameByPostCodeAndDeptId(se,deptId);
    }


    /**
     * todo since ruoyi3.8.0 用户授权信息
     * 根据用户编号获取授权角色
     *
     * @param userId
     * @return
     */
    @PreAuthorize(hasPermi ="system:user:query")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId)
    {
        AjaxResult ajax = success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * todo since ruoyi3.8.0 用户授权信息
     * 用户授权角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @PreAuthorize(hasPermi ="system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    {
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

}
