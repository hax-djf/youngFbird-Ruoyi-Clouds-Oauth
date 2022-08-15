package com.flybirds.oauth.aspect;

import com.alibaba.fastjson.JSON;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.LoginReqVo0;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.util.result.Result;
import com.flybirds.oauth.annotation.LoginSSO;
import com.flybirds.oauth.factory.UserLoginFactory;
import com.flybirds.oauth.manger.login.UserLoginHandler0;
import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;


/**
 * 登录切面处理
 *
 * @author flybirds
 */
@Aspect
@Component
public class LoginSSOAspect<T extends LoginReqVo0>
{
    @Value("${auth.clientId}")
    @Getter
    private String clientId;

    @Value("${auth.clientSecret}")
    @Getter
    private String clientSecret;

    private static final Logger log = LoggerFactory.getLogger(LoginSSOAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.flybirds.oauth.annotation.LoginSSO)")
    public void loginPointCut()
    {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "loginPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
    {
        handleLogin(joinPoint, null, jsonResult);
    }

    @Around(value = "loginPointCut()")
    public Result<AuthToken> loginAround0(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LoginSSO sso = getAnnotationLog(proceedingJoinPoint);
        UserLoginHandler0 handler0 = UserLoginFactory.get0(sso.SSOChannel());
        handler0.vaild(getLoginReqVo0(proceedingJoinPoint,sso.SSOChannel()));
        Object[] args = proceedingJoinPoint.getArgs();
        args[args.length -1] = handler0;
        Result<AuthToken> result = (Result<AuthToken>)proceedingJoinPoint.proceed(args);
        return result;
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "loginPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLogin(joinPoint, e, null);
    }

    protected void handleLogin(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try
        {
            // 获得注解
            LoginSSO sso = getAnnotationLog(joinPoint);
            if (sso == null)
            {
                return;
            }

            UserLoginHandler0 handler0 = UserLoginFactory.get0(sso.SSOChannel());
            AuthToken authToken = getAuthToken(jsonResult);

            if (e != null)
            {
                T loginReqVo0 = getLoginReqVo0(joinPoint);
                throw handler0.afterException(authToken,loginReqVo0,e);
            }
            handler0.afterCommitTask(authToken);
        }
        catch (Exception exp)
        {
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }


    T getLoginReqVo0(JoinPoint joinPoint) throws Exception {
        T loginReqVo0 = null;
        if (joinPoint.getArgs().length > 0) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                //请求参数类型判断过滤，防止JSON转换报错
                if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile) {
                    continue;
                }
                Object parse = args[i];
                if(parse instanceof LoginReqVo0){
                    loginReqVo0 = (T)parse;
                    break;
                }
            }
        }
        return loginReqVo0;
    }

    T getLoginReqVo0(JoinPoint joinPoint, AccountLoginChannelEnum channel) throws Exception {
        T loginReqVo0 = null;
        if (joinPoint.getArgs().length > 0) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                //请求参数类型判断过滤，防止JSON转换报错
                if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile) {
                    continue;
                }
                Object parse = args[i];
                if(parse instanceof LoginReqVo0){
                    loginReqVo0 = (T)parse;
                    loginReqVo0.setLoginChannel(Optional.ofNullable(loginReqVo0.getLoginChannel()).orElseGet(channel::getValue));
                    loginReqVo0.setClientId(Optional.ofNullable(loginReqVo0.getClientId()).orElseGet(this::getClientId));
                    loginReqVo0.setClientSecret(Optional.ofNullable(loginReqVo0.getClientSecret()).orElseGet(this::getClientSecret));
                    loginReqVo0.setGrantType(Optional.ofNullable(loginReqVo0.getGrantType()).orElseGet(channel::getGrantType));
                    break;
                }
            }
        }
        return loginReqVo0;
    }

    AuthToken getAuthToken(Object jsonResult) throws Exception {
        Object data = jsonResult != null ? ((Result) jsonResult).getData() : null;
        return data != null ? JSON.parseObject(JSON.toJSONString(data), AuthToken.class) : null;
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private LoginSSO getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(LoginSSO.class);
        }
        return null;
    }
}
