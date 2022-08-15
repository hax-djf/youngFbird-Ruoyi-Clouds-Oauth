package com.flybirds.security.core;

import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.text.Convert;
import com.flybirds.common.exception.CustomException;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全服务工具类,数据全部来自上下文数据【非缓存操作】
 *
 *  @author flybirds
 */
public class SecurityUtils {
    
    /**
     * 获取用户账号
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户账户异常", CodeConstant.Number.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户名称
     **/
    public static String getNickName() {
        try {
            return getLoginUser().getSysUser().getNickName();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户名称", CodeConstant.Number.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            throw new CustomException("获取登陆用户信息异常", CodeConstant.Number.UNAUTHORIZED);
        }
    }
    /**
     * 获取用户
     **/
    public static SysUser getSysUser() {
        try {
            return getLoginUser().getSysUser();
        }
        catch (Exception e) {
            throw new CustomException("获取用户详情信息异常", CodeConstant.Number.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户账号
     **/
    public static Long getUserId() {
        try {
            return Convert.toLong(getLoginUser().getUserId());
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户账户异常", CodeConstant.Number.UNAUTHORIZED);
        }
    }


    /**
     * 获取Authentication上下文对象
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /* 生成BCryptPasswordEncoder密码 */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /* 判断密码是否相同 */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /** 密码置空 */
    public static LoginUser ToPasswordNull(LoginUser loginUser) {
        loginUser.setPassWord(null);
        loginUser.getSysUser().setPassWord(null);
        return loginUser;
    }

    /** 获取请求token */
    public static String getToken() {
        return getToken(SecurityUtils.getRequest());
    }

    /**根据request请求头中获取请求token*/
    public static String getToken(HttpServletRequest request) {

        String token = request.getHeader(SecurityConstant.HEADER);
        if (StringUtils.isNotNull(token) && token.startsWith(SecurityConstant.TOKEN_PREFIX))
        {
            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        }
        return token;
    }

    /** 获取request */
    public static HttpServletRequest getRequest() {
        try {
            return getRequestAttributes().getRequest();
        }
        catch (Exception e) {
            return null;
        }
    }
    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        try {
            return getRequestAttributes().getResponse();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取ServletRequestAttributes
     * @return
     */
    private  static ServletRequestAttributes getRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) attributes;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }


    /***
     * 令牌解析之后获取令牌信息
     * @return
     */
    public String getAuthenticationByToken() {
        OAuth2AuthenticationDetails authentication = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return authentication.getTokenValue();
    }
    
}
