package com.flybirds.security.feign;

import com.flybirds.common.constant.SecurityConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;

/**
 * feign 请求拦截器
 *
 * @author flybirds
 */
@Component
@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final String STRINGREDISTEMPLATE = "stringRedisTemplate";

    @SuppressWarnings("all")
    @Resource(name = STRINGREDISTEMPLATE)
    private ValueOperations<String, Object> redissops;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        //TODO 出现主线程结束，子线程继续的操作,导致异常抛出 : https://blog.csdn.net/lvxiucai/article/details/101758179
        log.info("进入 OAuth2Feign拦截的线程名称 {}",Thread.currentThread().getName());
        RequestAttributes requestAttributes = null;
        try {
            requestAttributes = RequestContextHolder.currentRequestAttributes();

        }catch (RuntimeException e){
            requestAttributes = null;
            log.info("{} No thread-bound request ","RequestContextHolder.currentRequestAttributes() 主线程已经结束");
            log.error(e.getMessage());
        }
//        if (requestAttributes != null) {
//            requestTemplate.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
//            requestTemplate.header("User-Ip","127.0.0.1");
//        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails)
        {
            /* 使用requestTemplate的时候携带请求 */
            OAuth2AuthenticationDetails dateils = (OAuth2AuthenticationDetails) authentication.getDetails();
            requestTemplate.header(HttpHeaders.AUTHORIZATION,
                                    String.format("%s%s", SecurityConstant.TOKEN_PREFIX, dateils.getTokenValue()))
                           .header(SecurityConstant.SERVICE_SIGNATURE,SecurityConstant.SERVICE_SIGNATURE_VALUE);

        }
    }
}