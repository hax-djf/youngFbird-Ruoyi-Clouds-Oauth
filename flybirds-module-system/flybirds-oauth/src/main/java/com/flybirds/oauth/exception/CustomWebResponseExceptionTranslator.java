package com.flybirds.oauth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import java.nio.file.AccessDeniedException;
import java.util.concurrent.TimeoutException;

/**
 * OAuth2 自定义异常处理
 * 这里主要的实现来自与 {@link org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#handleException(OAuth2Exception)}
 * 是通过全局异常处理器，将适配的在认证处理器中的 CustomWebResponseExceptionTranslator 进调用处理的
 *  @author: ruoyi
 */
@Slf4j
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        /**
         * 1.异常链中包含Http方法请求异常
         * 2.异常链中包含拒绝访问异常
         * 3.身份验证相关异常
         * 4.异常链中有OAuth2Exception异常
         */
        if(e instanceof OAuth2Exception
                || e instanceof AuthenticationException
                || e instanceof AccessDeniedException
                || e instanceof HttpRequestMethodNotSupportedException){

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CustomOauthException(e.getMessage()));
        }

        if(e instanceof TimeoutException){

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CustomOauthException("网络状态不太好，请重试！"));
        }

        log.info("ouath2.0 出现其他认证信息，请管快速排查异常：{}",e.getMessage());
        //fakafa日志通知
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomOauthException("系统错误，请联系管理员！"));
    }
}
