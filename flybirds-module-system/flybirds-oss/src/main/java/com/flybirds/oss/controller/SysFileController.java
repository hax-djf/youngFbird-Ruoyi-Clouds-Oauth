package com.flybirds.oss.controller;


import com.flybirds.api.core.entity.SysFile;
import com.flybirds.common.util.result.Result;
import com.flybirds.oss.service.ISysFileService;
import com.flybirds.oss.utils.FileUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @description: 通用文件请求处理
 * @author ruoyi
 */
@RestController
public class SysFileController {

    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public Result<SysFile> upload(MultipartFile file) {
        try
        {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = FileUploadUtils.buildSysFile(file, url);
            return Result.ok(sysFile);
        }
        catch (Exception e)
        {
            log.error("上传文件失败", e);
            return Result.fail(e.getMessage());
        }
    }
}