package com.flybirds.web.web.core.filter;

import cn.hutool.core.util.StrUtil;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.common.util.str.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static com.flybirds.common.exception.enums.GlobalErrorCodeConstants.DEMO_DENY;

/**
 * 演示 Filter，禁止用户发起写操作，避免影响测试数据
 *
 * @author 芋道源码
 */
public class DemoFilter extends OncePerRequestFilter {

    private String[] demoIgnore = new String[]{
            "/token/**","/register/**","/operlog/**","/logininfor/**","/social/**","/oauth/**","/send/**",
            "/log/**","/retrieve/**"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String method = request.getMethod();
        return StringUtils.matches(request.getRequestURI(), Arrays.asList(demoIgnore)) || !StrUtil.equalsAnyIgnoreCase(method, "POST", "PUT", "DELETE");  // 写操作时，不进行过滤
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        // 直接返回 DEMO_DENY 的结果。即，请求不继续
        ServletUtils.writeJSON(response, AjaxResult.error(DEMO_DENY));
    }

}
