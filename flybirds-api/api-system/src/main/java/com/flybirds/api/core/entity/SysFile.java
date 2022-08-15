package com.flybirds.api.core.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 文件信息
 * 
 * @author ruoyi
 */
public class SysFile implements Serializable {
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件大小
     * @return
     */
    private Long size;

    /**
     * 文件类型
     * @return
     */
    private String fileType;

    /**
     * 文件详情信息
     */
    private Object object;


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("url", getUrl())
            .append("size",getSize())
            .append("fileType",getFileType())
            .toString();
    }
}
