package com.flybirds.mybatis.core.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flybirds.common.util.str.StringUtils;

/**
 * 分页数据
 *
 * @author ruoyi
 */
public class MpPageDomain {
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    public boolean getOrderByAsc() {

        boolean flag;
        if (StringUtils.isEmpty(orderByColumn)) {
            flag = true;
        }else if(ASC.equals(isAsc)) {
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + getIsAsc();
    }

    public  void  buildOrderBy(Page page){
        if(StringUtils.isNotNull(orderByColumn) && getOrderByAsc()){
            if(orderByColumn.contains(",")){
                page.addOrder(OrderItem.ascs(orderByColumn.split(",")));
            }else{
                page.addOrder(OrderItem.asc(orderByColumn));
            }
        }else if(StringUtils.isNotNull(orderByColumn) && !getOrderByAsc()){
            if(orderByColumn.contains(",")){
                page.addOrder(OrderItem.descs(orderByColumn.split(",")));
            }else{
                page.addOrder(OrderItem.desc(orderByColumn));
            }
        }
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn()
    {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn)
    {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc()
    {
        if(isAsc == null){
            isAsc = DESC;
        }
        return isAsc;
    }

    public void setIsAsc(String isAsc)
    {
        this.isAsc = isAsc;
    }

    public final static String  ASC= "asc";
    public final static String  DESC= "desc";

}
