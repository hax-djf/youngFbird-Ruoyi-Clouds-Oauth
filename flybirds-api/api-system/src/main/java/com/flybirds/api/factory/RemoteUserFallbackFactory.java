package com.flybirds.api.factory;

import com.flybirds.api.RemoteUserService;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.util.result.Result;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 * 
 * @author flybirds
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable) {

        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService()

        {
            @Override
            public Result<UserInfo> getUserInfo(String username)
            {
                return Result.fail("用户名获取用户失败:" + throwable.getMessage());
            }

            @Override
            public Result<UserInfo> getUserInfoById(Long id) {
                return Result.fail("用户Id获取用户失败:" + throwable.getMessage());
            }

            @Override
            public Result<UserInfo> getUserInfoByEmail(String email)
            {
                return Result.fail("邮箱获取用户失败:" + throwable.getMessage());
            }

            @Override
            public Result<UserInfo> getUserInfoByMobile(String mobile)
            {
                return Result.fail("手机号获取用户失败:" + throwable.getMessage());
            }

            @Override
            public Result<SysUser> getUserByMobile(String mobile){

                return Result.fail("手机获取单个用户失败:" + throwable.getMessage());
            }

            @Override
            public Result<SysUser> getUserByUserName(String userName) {
                return Result.fail("用户名获取单个用户失败:" + throwable.getMessage());
            }

            @Override
            public Result<String> checkPhoneValid(String mobile) {

                return Result.fail("根据手机号判断手机号有效性失败:" + throwable.getMessage());
            }
        };
    }
}
