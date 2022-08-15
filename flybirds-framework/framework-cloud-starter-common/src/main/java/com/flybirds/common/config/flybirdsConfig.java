package com.flybirds.common.config;

import com.flybirds.common.constant.Constant;
import com.flybirds.common.util.str.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author ruoyi
 */
@Component
@Configuration   //配置类注解，被自动扫描发现
//@PropertySource("classpath:applicatipn.yml") //指明配置源文件位置,但是这里需要注意使用nacos的时候，也必须使用application.yml才能生效
@ConfigurationProperties(prefix = "flybirds.info")
public class flybirdsConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 上传路径 */
    private static String profile = Constant.RESOURCE_PREFIX;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    /* 缓存开关 **/
    private static String  cacheEnabled;

    /** base包名 */
    private String basePackage;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        flybirdsConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        flybirdsConfig.addressEnabled = addressEnabled;
    }

    /**
     * 缓存开关
     */
    public static String isCacheEnabled()
    {
        return StringUtils.nvl(cacheEnabled, "true");
    }
    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
    /**
     * 获取附件上传路径
     */
    public static String getAttachPath()
    {
        return getProfile() + "/attach";
    }
    /**
     * 获取素材上传路径
     */
    public static String getMaterialPath()
    {
        return getProfile() + "/material";
    }
    /**
     * 获取资源上传路径
     */
    public static String getResourcePath()
    {
        return getProfile() + "/resource";
    }
    /**
     * 获取模板上传路径
     */
    public static String getTemplatePath()
    {
        return getProfile() + "/template";
    }
    /**
     * 获取插件上传路径
     */
    public static String getPlugPath()
    {
        return getProfile() + "/plugs";
    }
    /**
     * 获取数据库备份路径
     */
    public static String getDbBackupPath()
    {
        return getProfile() + "/dbbackup";
    }

}
