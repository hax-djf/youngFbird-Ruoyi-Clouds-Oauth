package com.flybirds.mybatis.core.basepojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree基类
 *
 * @author ruoyi
 */
public class MpTreeEntity<E> extends MpBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 父菜单名称 */
    private String parentName;

    /** 父菜单ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 子部门 */
    private List<E> children = new ArrayList<>();

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public List<E> getChildren()
    {
        return children;
    }

    public void setChildren(List<E> children)
    {
        this.children = children;
    }
}
