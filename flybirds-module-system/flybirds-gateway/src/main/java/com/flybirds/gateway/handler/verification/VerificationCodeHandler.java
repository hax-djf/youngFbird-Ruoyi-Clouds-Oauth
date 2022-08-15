package com.flybirds.gateway.handler.verification;


import com.flybirds.common.exception.CaptchaException;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.gateway.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 路由映射handler实现验证码获取
 *
 * @author: ruoyi
 */
@Component
public class VerificationCodeHandler implements HandlerFunction<ServerResponse> {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        AjaxResult ajax;
        try {
            ajax = verificationCodeService.createCapcha();
        } catch (CaptchaException | IOException e) {
            return Mono.error(e);
        }
            return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ajax));
    }
}