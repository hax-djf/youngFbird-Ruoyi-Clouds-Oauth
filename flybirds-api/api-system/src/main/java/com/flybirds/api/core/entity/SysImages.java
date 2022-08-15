package com.flybirds.api.core.entity;

import java.io.Serializable;

/**
 * @author :ruoyi
 * @create :2021-08-12 10:40:00
 * @description :
 */
public class SysImages implements Serializable{

    /**
     * 文件缩略图 只考虑图片存在
     * @return
     */
    private String thumbnailUrl;

    /**
     * 图片高
     * @return
     */
    private Integer height;

    /**
     * 图片宽
     * @return
     */
    private Integer width;

    /**
     * 后缀
     * @return
     */
    private String suffix;


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
