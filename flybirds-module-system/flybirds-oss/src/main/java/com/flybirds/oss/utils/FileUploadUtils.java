package com.flybirds.oss.utils;

import com.flybirds.api.core.entity.SysFile;
import com.flybirds.api.core.entity.SysImages;
import com.flybirds.common.exception.file.FileNameLengthLimitExceededException;
import com.flybirds.common.exception.file.FileSizeLimitExceededException;
import com.flybirds.common.exception.file.InvalidExtensionException;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.common.util.file.MimeTypeUtils;
import com.flybirds.common.util.idtool.IdUtils;
import com.flybirds.common.util.str.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.flybirds.common.util.file.MimeTypeUtils.IMAGE_EXTENSION;


/**
 * 本地文件上传工具类
 * 
 * @author ruoyi
 */
public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 资源映射路径 前缀
     */
    @Value("${file.prefix}")
    public String localFilePrefix;

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
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension
     * @return String 上传文件类型
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws IOException 比如读写文件出错时
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(fileName);
        return pathFileName;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
        return fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {

        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists())
        {
            if (!desc.getParentFile().exists())
            {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    private static final String getPathFileName(String fileName) throws IOException
    {
        String pathFileName = "/" + fileName;
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
            if (allowedExtension == IMAGE_EXTENSION)
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
     * 文件数据的组装
     * @param file
     * @param url
     * @return
     */
    public static final SysFile buildSysFile(MultipartFile file, String url) {

      String extension = getExtension(file);
      SysFile sysFile = new SysFile();
      sysFile.setName(file.getOriginalFilename());
      sysFile.setUrl(url);
      sysFile.setSize(file.getSize());

      // 1 图片 2 视频 3 文本 4 音频 5 压缩 6 其他
      sysFile.setFileType("6");

      //ArrayUtils 使用: https://www.cnblogs.com/jpfss/p/10756976.html
      if(ArrayUtils.contains(IMAGE_EXTENSION,extension) && isAskedByUrl(url)){
          SysImages sysImages = new SysImages();
          int[] imageWidthAndHeight = getImageWidthAndHeightArrByUrl(url);
          sysImages.setWidth(imageWidthAndHeight[0]);
          sysImages.setHeight(imageWidthAndHeight[1]);
          sysImages.setSuffix(extension);
          sysImages.setThumbnailUrl(url);
          sysFile.setObject(sysImages);
          sysFile.setFileType("1");
      }

      return sysFile;

    }
    /**
     * 根据图片地址解析图片的高度和宽度
     * @param url
     * @return
     */
    public static final int[] getImageWidthAndHeightArrByUrl(String url) {

        BufferedImage sourceImg = null;
        int width=1;
        int height=0;

        int[] result=new int[2];
        try {
            InputStream murl = new URL(url).openStream();
            sourceImg = ImageIO.read(murl);
            width = sourceImg.getWidth();
            height = sourceImg.getHeight();
            result[0]=width;
            result[1]=height;
        } catch (Exception e) {
            e.printStackTrace();
            result[0]=1;
            result[1]=0;
            log.info("Img Util getImageWidthAndHeightArrByUrl:error", e);
        }
        return result;
    }
    public static int[] getImageWidthAndHeightArrByUrl(byte[] imageByte){
        InputStream is =new ByteArrayInputStream(imageByte);//通过文件的字节数组读取
        BufferedImage buff;
        int width=0;
        int height=0;
        int[] result=new int[2];
        try {
            buff = ImageIO.read(is);
            width=buff.getWidth();//得到图片的宽度
            height=buff.getHeight();//得到图片的高度
            result[0]=width;
            result[1]=height;
        } catch (Exception e) {
            e.printStackTrace();
            result[0]=1;
            result[1]=0;
            log.info("Img Util getImageWidthAndHeightArr:error", e);
        }

        return result;
    }
    /**
     * 网络图片是否可以访问
     * @param urlStr
     * @return
     */
    public static boolean isAskedByUrl(String urlStr){
        try {
            URL url = null;
            url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int state = con.getResponseCode();
            if (state == 200) {
                return true;
            }else{
                log.info("HTTP访问图片:"+urlStr+"访问不到");
                return false;
            }
        } catch (Exception e) {
            log.error("图片:"+urlStr+"访问不到",e);
            return false;
        }
    }
}