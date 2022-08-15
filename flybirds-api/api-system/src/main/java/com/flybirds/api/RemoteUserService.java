package com.flybirds.api;

import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.factory.RemoteUserFallbackFactory;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.util.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient-用户服务
 *
 * @author flybirds
 *
 * 注意点：这里降级服务引入的包是 feign.hystrix.FallbackFactory;
 *        但是我们是使用的sentinel是没问题的
 *        其实不管是Hystrix还是Sentinel对于Feign的支持，核心代码基本上是一致的，只需要修改依赖和配置文件即可。
 * 不管是get还是post都要写全参数的注解
 */
@FeignClient(contextId = "remoteUserService", value = Constant.CloudServiceName.FLYBIRDS_SYSTEM, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {
    /**
     * 用户名查询用户信息
     *
     * @param userName 用户名
     * @return 结果
     */
     @GetMapping(value = "/user/open/info/userName/{userName}")
     Result<UserInfo> getUserInfo(@PathVariable("userName") String userName);

    /**
     * 通过用户Id查询用户信息【权限信息】
     *
     * @param id 用户名
     * @return 结果
     */
     @GetMapping(value = "/user/open/info/id/{id}")
     Result<UserInfo> getUserInfoById(@PathVariable("id") Long id);

    /**
     * 通过邮箱查询用户信息【部分权限信息】
     *
     * @param email 用户名
     * @return 结果
     */
     @GetMapping(value = "/user/open/info/email/{email}")
     Result<UserInfo> getUserInfoByEmail(@PathVariable("email") String email);

    /**
     * 通过手机号查询用户信息【权限信息】
     *
     * @param mobile 用户名
     * @return 结果
     */
     @GetMapping(value = "/user/open/info/mobile/{mobile}")
     Result<UserInfo> getUserInfoByMobile(@PathVariable("mobile") String mobile);

    /**
     * 通过手机号查询用户信息【无权限信息】
     *
     * @param mobile 用户名
     * @return 结果
     */
     @GetMapping(value = "/user/open/info/single/mobile/{mobile}")
     Result<SysUser> getUserByMobile(@PathVariable("mobile") String mobile);

    /**
     * 通过手机号查询用户信息【无权限信息】
     *
     * @param userName 用户名
     * @return 结果
     */
     @GetMapping(value = "/user/open/info/single/userName/{userName}")
     Result<SysUser> getUserByUserName(@PathVariable("userName") String userName);

    /**
     * 校验手机号有效性
     * @param phone
     * @return
     */
     @GetMapping(value = "/user/open/check/phoneValid/{phone}")
     Result<String> checkPhoneValid(@PathVariable(value = "phone") String phone);


}
