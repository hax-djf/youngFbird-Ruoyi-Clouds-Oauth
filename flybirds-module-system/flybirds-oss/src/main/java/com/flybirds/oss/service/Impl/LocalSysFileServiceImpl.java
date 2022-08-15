package com.flybirds.oss.service.Impl;

import com.flybirds.oss.mapper.SysFileInfoMapper;
import com.flybirds.oss.service.ISysFileService;
import com.flybirds.oss.utils.FileUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本地文件存储
 * 
 * @author ruoyi
 */
//@Primary
@Service
@SuppressWarnings("all")
public class LocalSysFileServiceImpl implements ISysFileService {

    private static final Logger log = LoggerFactory.getLogger(LocalSysFileServiceImpl.class);

    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    /**
     * 资源映射路径 前缀
     */
    @Value("${file.prefix}")
    public String localFilePrefix;

    /**
     * 域名或本机访问地址
     */
    @Value("${file.domain}")
    public String domain;
    
    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return String 相对应用的基目录
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception
    {
        String name = FileUploadUtils.upload(localFilePath, file);
        String url = domain + localFilePrefix + name;
        return url;
    }

}
