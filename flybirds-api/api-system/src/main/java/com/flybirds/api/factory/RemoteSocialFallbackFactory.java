package com.flybirds.api.factory;

import com.flybirds.api.RemoteSocialService;
import com.flybirds.api.core.vo.AuthSocialLoginReqVO;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.result.Result;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteSocialFallbackFactory implements FallbackFactory<RemoteSocialService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteSocialFallbackFactory.class);

    @Override
    public RemoteSocialService create(Throwable throwable) {

        log.error("社交服务调用失败:{}", throwable.getMessage());
        return new RemoteSocialService()

        {
            @Override
            public Result<String> socialLogin(@Valid AuthSocialLoginReqVO reqVO) {
                return Result.fail("社交登录获取用户数据失败");
            }

            @Override
            public Result<List<KeyValue<String, String>>> socialLogin(@Valid SocialLoginReqVo0 reqVo0) {
                return Result.fail("社交登录获取用户数据失败");
            }

            @Override
            public Result<Boolean> socialLoginBind2(@Valid SocialLoginReqVo0 reqVO, Long userId) {
                return Result.fail(false);
            }
        };
    }
}
