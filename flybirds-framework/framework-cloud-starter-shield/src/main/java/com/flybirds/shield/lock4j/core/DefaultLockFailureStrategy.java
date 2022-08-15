package com.flybirds.shield.lock4j.core;

import com.baomidou.lock.LockFailureStrategy;
import com.flybirds.common.exception.ServiceException;
import com.flybirds.common.exception.enums.GlobalErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义获取锁失败策略，抛出 {@link ServiceException} 异常
 */
@Slf4j
public class DefaultLockFailureStrategy implements LockFailureStrategy {

    @Override
    public void onLockFailure(String key, long acquireTimeout, int acquireCount) {
        log.debug("[onLockFailure][线程:{} 获取锁失败，key:{} 获取超时时长:{} ms]", Thread.currentThread().getName(), key, acquireTimeout);
        throw new ServiceException(GlobalErrorCodeConstants.LOCKED);
    }

}
