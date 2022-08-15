package com.flybirds.api;
import com.flybirds.api.core.entity.SysFile;
import com.flybirds.api.core.entity.SysFileInfo;
import com.flybirds.api.factory.RemoteFileFallbackFactory;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.util.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 *  1.@RequestPart这个注解用在multipart/form-data表单提交请求的方法上。
 *  2.支持的请求方法的方式MultipartFile，属于Spring的MultipartResolver类。这个请求是通过http协议传输的。
 *  3.@RequestParam也同样支持multipart/form-data请求。
 *  4.他们最大的不同是，当请求方法的请求参数类型不再是String类型的时候。
 *  5.@RequestParam适用于name-valueString类型的请求域，@RequestPart适用于复杂的请求域（像JSON，XML）。
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "remoteFileService", value = Constant.CloudServiceName.FLYBIRDS_MANGEFILE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService
{
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<SysFile> upload(@RequestPart(value = "file") MultipartFile file);

    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/uploadByDir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadByDir(@RequestParam(value = "dir") String dir, @RequestPart(value = "file") MultipartFile file);

    /**
     * 保存系统文件
     * @param sysFileInfo 系统文件
     * @return 结果
     */
    @PostMapping("/insertFile")
    Result<Boolean> saveFile(@RequestBody SysFileInfo sysFileInfo);
}
