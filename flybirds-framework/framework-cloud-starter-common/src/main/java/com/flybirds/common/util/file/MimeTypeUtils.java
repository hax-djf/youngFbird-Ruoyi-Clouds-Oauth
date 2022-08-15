package com.flybirds.common.util.file;

/**
 * 媒体类型工具类
 * 
 * @author 米饭饭一族
 */
public class MimeTypeUtils {

    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";

    /**
     * 图片
     */
    public static final String MATERIAL_TYPE_IMG="1";
    /**
     * 视频
     */
    public static final String MATERIAL_TYPE_VIDEO="2";
    /**
     * 文本
     */
    public static final String MATERIAL_TYPE_TEXT="3";
    /**
     * 音频
     */
    public static final String MATERIAL_TYPE_AUDIO="4";
    /**
     * 压缩
     */
    public static final String MATERIAL_TYPE_ZIP="5";
    /**
     * 其它
     */
    public static final String MATERIAL_TYPE_OTHER="6";

    
    public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

    public static final String[] FLASH_EXTENSION = { "swf", "flv" };

    public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb" };

    public static final String[] VIDEO_EXTENSION = { "mp4", "avi", "rmvb" };

    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf" };

    public static String getExtension(String prefix)
    {
        switch (prefix)
        {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }
    public static final String[] ZIP_EXTENSION = {"rar", "zip", "gz", "bz2","jar"};
}
