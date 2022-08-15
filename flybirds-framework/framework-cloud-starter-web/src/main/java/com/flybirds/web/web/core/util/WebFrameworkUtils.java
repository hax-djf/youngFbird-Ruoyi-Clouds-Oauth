package com.flybirds.web.web.core.util;

import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.enums.user.UserTypeEnum;
import com.flybirds.web.web.core.util.jwt.JwtUtils;
import com.flybirds.web.web.core.util.vo.FlybirdsClaims;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 芋道源码
 * @description 专属于 web 包的工具类
 */
public class WebFrameworkUtils {

    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "result";

    public static Integer getUserType(HttpServletRequest request) {
        // TODO 等后续优化处理，默认先返回一个身份
        return UserTypeEnum.MEMBER.getValue();
    }

    public static FlybirdsClaims getUserClaims(HttpServletRequest request) {
        FlybirdsClaims claimByToken = null;
        if(request.getHeader(SecurityConstant.HEADER) != null){
             claimByToken = JwtUtils.getClaimByToken(request.getHeader(SecurityConstant.HEADER));
        }
        return claimByToken;
    }

    public static Long getUserId(HttpServletRequest request) {
        return getUserClaims(request).buildUserId();
    }

    public static String getUserName(HttpServletRequest request) {
        return getUserClaims(request).buildUserName();
    }

    public static void setObjectResult(ServletRequest request, Object result) {

        request.setAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT, result);
    }

    public static Object getObjectResult(ServletRequest request) {

        return request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
    }
}
