package com.flybirds.api;

import com.flybirds.api.core.vo.AuthSocialLoginReqVO;
import com.flybirds.api.factory.RemoteSocialFallbackFactory;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * FeignClient- 社交登录用户服务
 *
 * @author flybirds
 *
 * 注意点：这里降级服务引入的包是 feign.hystrix.FallbackFactory;
 *        但是我们是使用的sentinel是没问题的
 *        其实不管是Hystrix还是Sentinel对于Feign的支持，核心代码基本上是一致的，只需要修改依赖和配置文件即可。
 * 不管是get还是post都要写全参数的注解
 */
@FeignClient(contextId = "remoteSocialService", value = Constant.CloudServiceName.FLYBIRDS_SYSTEM, fallbackFactory = RemoteSocialFallbackFactory.class)
public interface RemoteSocialService {

    /**
     *  社交登录获取用户名
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/social/open/social-login")
    Result<String> socialLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO);


    @PostMapping(value = "/social/open/social-login0")
    Result<List<KeyValue<String,String>>> socialLogin(@RequestBody @Valid SocialLoginReqVo0 reqVo0);

    @PostMapping(value = "/social/open/social-bind/{userId}")
    Result<Boolean> socialLoginBind2(@RequestBody @Valid SocialLoginReqVo0 reqVO,
                                     @PathVariable(value = "userId") Long userId);

}
