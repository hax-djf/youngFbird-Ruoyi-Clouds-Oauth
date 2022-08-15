package com.flybirds.security.model;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.util.ip.IpUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.servlet.ServletUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户身份权限
 *
 * @author flybirds
 */
public class LoginUser extends User{

    private static final long serialVersionUID = 1L;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 用户名id
     */
    private Long userId;

    /**
     * 密码信息
     */
    private String passWord;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     *用户信息
     */
    private SysUser sysUser;

    /**
     * 系统信息
     */

    private String userAgent;

    /**
     * 用户ip
     */
    private String userIp;


    //【注意：使用json的时候找的第一个构造方法，不要更换随意跟换位置】
    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Set<String> permissions, Set<String> roles, Long userId, String passWord, String userName, Long expireTime, SysUser sysUser, String userAgent, String userIp) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.permissions = permissions;
        this.roles = roles;
        this.userId = userId;
        this.passWord = passWord;
        this.userName = userName;
        this.expireTime = expireTime;
        this.sysUser = sysUser;
        this.userAgent = userAgent;
        this.userIp = userIp;
    }

    public LoginUser(Long userId, String username, String password, boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, SysUser sysUser, UserInfo userInfo) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.sysUser = sysUser;
        this.passWord = password;
        this.userName = username;
        this.userId = userId;
        this.permissions= userInfo.getPermissions();
        this.roles = userInfo.getRoles();
        this.userAgent = UserAgentUtils.getUserAgent(ServletUtils.getRequest());
        //todo 考虑两种情况，授权的方式，系统适配登录，第一种是不会存在user-ip的属性操作的，需要额外处理
        String ipAddr = IpUtils.getOauthUserIpAddr(ServletUtils.getRequest());
        this.userIp = ObjectUtil.isNull(ipAddr) ? IpUtils.getIpAddr(ServletUtils.getRequest()): ipAddr;
    }

    public LoginUser(Long userId, String username, String password, boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.passWord = password;
        this.userName = username;
        this.userId = userId;
    }

    @Override
    public String getPassword()
    {
        return this.passWord;
    }

    @Override
    public String getUsername()
    {
        return this.userName;
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }


    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
}
