package com.flybirds.shield.idempotent.core.keyresolver;

import com.flybirds.shield.idempotent.core.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * 幂等 Key 解析器接口
 *
 * @author flybirds
 */
public interface IdempotentKeyResolver {

    /**
     * 解析一个 Key
     *
     * @param idempotent 幂等注解
     * @param joinPoint  AOP 切面
     * @return Key
     *
     * 解析aop中 joinPoint切面
     * JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象.
     * 1.Signature getSignature(); 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
     * 2.Object[] getArgs(); 获取传入目标方法的参数对象
     * 3.Object getTarget(); 获取被代理的对象
     * 4.Object getThis(); 获取代理对象
     *
     * 参考文章解析joinPoint切面地址 : https://blog.csdn.net/qq_15037231/article/details/80624064
     */
    String resolver(JoinPoint joinPoint, Idempotent idempotent);

}
