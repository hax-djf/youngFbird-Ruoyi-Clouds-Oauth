package com.flybirds.api.factory;

import com.flybirds.api.RemoteFileService;
import com.flybirds.api.core.entity.SysFile;
import com.flybirds.api.core.entity.SysFileInfo;
import com.flybirds.common.util.result.Result;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteFileService create(Throwable throwable)
    {
        log.error("文件服务调用失败:{}", throwable.getMessage());
        return new RemoteFileService()
        {
            @Override
            public Result<SysFile> upload(MultipartFile file)
            {
                return Result.fail("文件服务熔断降级处理,上传文件失败:" + throwable.getMessage());
            }

            @Override
            public Result<String> uploadByDir(String dir, MultipartFile file) {
                return Result.fail("文件服务熔断降级处理,上传文件失败:" + throwable.getMessage());
            }

            @Override
            public Result<Boolean> saveFile(SysFileInfo sysFileInfo)
            {
                return Result.fail("文件服务熔断降级处理,文件入库失败:" + throwable.getMessage());
            }
        };
    }
}
