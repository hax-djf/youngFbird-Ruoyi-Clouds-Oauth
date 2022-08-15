package com.flybirds.web.web.core.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HTMLFilter;
import com.flybirds.common.util.servlet.ServletUtils;
import lombok.Getter;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Xss 请求 Wrapper
 *
 * @author flybirds
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    //没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
    HttpServletRequest orgRequest;

    @Getter
    private String content;

    /**
     * 基于线程级别的 HTMLFilter 对象，因为它线程非安全
     */
    private static final ThreadLocal<HTMLFilter> HTML_FILTER = ThreadLocal.withInitial(() -> {
        HTMLFilter htmlFilter = new HTMLFilter();
        // 反射修改 encodeQuotes 属性为 false，避免 " 被转移成 &quot; 字符
        ReflectUtil.setFieldValue(htmlFilter, "encodeQuotes", false);
        return htmlFilter;
    });

    //html的编码过滤动作
    private String xssEncode(String input) {
        return HTML_FILTER.get().filter(input);
    }


    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
        //原始的request
        orgRequest = request;
    }

    private static String filterXss(String content) {
        if (StrUtil.isEmpty(content)) {
            return content;
        }
        return HTML_FILTER.get().filter(content);
    }

    // ========== IO 流相关 ==========
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 如果非 json 请求，不进行 Xss 处理
        if (!ServletUtils.isJsonRequest(this)) {
            return super.getInputStream();
        }

        // 读取内容，并过滤
        String content = IoUtil.readUtf8(super.getInputStream());
        content = filterXss(content);
        final ByteArrayInputStream newInputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
        // 返回 ServletInputStream
        return new ServletInputStream() {
            @Override
            public int read() {
                return newInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {}

        };
    }

    // ========== Param 相关 ==========
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return filterXss(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtil.isEmpty(values)) {
            return values;
        }
        // 过滤处理
        for (int i = 0; i < values.length; i++) {
            values[i] = filterXss(values[i]);
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> valueMap = super.getParameterMap();
        if (CollUtil.isEmpty(valueMap)) {
            return valueMap;
        }
        // 过滤处理
        for (Map.Entry<String, String[]> entry : valueMap.entrySet()) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = filterXss(values[i]);
            }
        }
        return valueMap;
    }

    // ========== Header 相关 ==========
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return filterXss(value);
    }

    /**
     * 获取最原始的request
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

}
