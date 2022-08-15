package com.flybirds.common.util.file;

import com.flybirds.common.config.flybirdsConfig;
import com.flybirds.common.exception.file.FileNameLengthLimitExceededException;
import com.flybirds.common.exception.file.FileSizeLimitExceededException;
import com.flybirds.common.exception.file.InvalidExtensionException;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.common.util.idtool.IdUtils;
import com.flybirds.common.util.sign.Md5Utils;
import com.flybirds.common.util.str.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件上传工具类
 *
 * @author ruoyi
 */
public class LocalFileUploadUtils
{
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = flybirdsConfig.getProfile();

    public static void setDefaultBaseDir(String defaultBaseDir)
    {
        LocalFileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir()
    {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file) throws IOException
    {
        try
        {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > LocalFileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(LocalFileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        return pathFileName;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
        return fileName;
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }
    /**
     * 删除素材文件
     * @param fileName  /profile/material/2019/11/06/f4395e7b74fe673893ffd7d2f317dbdc.png
     * @return
     */
    public static final boolean deleteFile(String fileName) throws Exception{
        File target=  getAbsoluteFile(flybirdsConfig.getProfile(),fileName.replace(flybirdsConfig.getResourcePath(),""));
        if(target.exists()&&target.isFile()){
            target.delete();
            return true;
        }
        return false;
    }
    public static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        int dirLastIndex = flybirdsConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = flybirdsConfig.getResourcePath() + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }


    /**
     * 是否图片
     * @param suffix
     * @return
     */
    public static final boolean isImage(String suffix){
        for (String str : MimeTypeUtils.IMAGE_EXTENSION)
        {
            if (str.equalsIgnoreCase(suffix))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否网络图片
     * @param url
     * @return
     */
    public static final boolean isImageUrl(String url){
        if(StringUtils.isEmpty(url)||!url.startsWith("http")){
            return false;
        }
        String suffix=url.substring(url.lastIndexOf(".")+1);
        if(isImage(suffix)){
            return true;
        }
        return false;
    }
    public static boolean checkImageUrlExists(String url){
        try{
            URL u = new URL(url);
            HttpURLConnection urlcon = (HttpURLConnection) u.openConnection();
            if(urlcon.getResponseCode()==200){
                return true;
            }
        }catch (Exception ex){
        }
        return false;
    }
    public static final boolean isText(String suffix){
        for (String str : MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION)
        {
            if (str.equalsIgnoreCase(suffix))
            {
                return true;
            }
        }
        return false;
    }

    public static final boolean isVideo(String suffix){
        for (String str : MimeTypeUtils.VIDEO_EXTENSION)
        {
            if (str.equalsIgnoreCase(suffix))
            {
                return true;
            }
        }
        return false;
    }
    public static final boolean isAudio(String suffix){
        for (String str : MimeTypeUtils.MEDIA_EXTENSION)
        {
            if (str.equalsIgnoreCase(suffix))
            {
                return true;
            }
        }
        return false;
    }
    public static final boolean isZip(String suffix){
        for (String str : MimeTypeUtils.ZIP_EXTENSION)
        {
            if (str.equalsIgnoreCase(suffix))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * 获取图片宽度
     * @param file  图片文件
     * @return 宽度
     */
    public static int getImgWidth(MultipartFile file) {
        String suffix=  getExtension(file);
        if(!isImage(suffix)){
            return 0;
        }
        BufferedImage src = null;
        int ret = -1;
        try {
            src = javax.imageio.ImageIO.read(file.getInputStream());
            ret = src.getWidth(null); // 得到源图宽
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 获取图片高度
     * @param file  图片文件
     * @return 高度
     */
    public static int getImgHeight(MultipartFile file) {
        String suffix=  getExtension(file);
        if(!isImage(suffix)){
            return 0;
        }
        BufferedImage src = null;
        int ret = -1;
        try {
            src = javax.imageio.ImageIO.read(file.getInputStream());
            ret = src.getHeight(null); // 得到源图高
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    private static int counter = 0;
    /**
     * 编码文件名
     */
    public static final String encodingFilename(String fileName)
    {
        fileName = fileName.replace("_", " ");
        fileName = Md5Utils.hash(fileName + System.nanoTime() + counter++);
        return fileName;
    }

}
