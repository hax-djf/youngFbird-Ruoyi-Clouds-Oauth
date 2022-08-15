package com.flybirds.mybatis.core.condition;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flybirds.common.util.str.StringUtils;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * post请求参数DTO
 *
 * @author : flybirds
 */
public class Params {
    /**
     * 条件参数
     */
    private Map<String, Object> conditions;

    /** 搜索值 */
    private String customValue;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    /**
     * 当前页
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum = PAGE_NUM;
    /**
     * 当前页数据量大小
     */
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Max(value = 500, message = "页码最大值为 500")
    private Integer pageSize = PAGE_SIZE;

    /**
     * 适配mybatis_plus默认值操作
     * @return
     */
    public void setDefaultPage() {

        if (StringUtils.isNull(this.getPageSize()) || this.getPageSize() == 0) {
            this.setPageSize(Params.PAGE_SIZE);
        }
        if (StringUtils.isNull(this.getPageNum())|| this.getPageNum() == 0) {
            this.setPageNum(Params.PAGE_NUM);
        }
        if (StringUtils.isNull(this.getConditions())) {
            this.setConditions(new HashMap<String, Object>());
        }
    }
    /* 获取排序方式 */
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

    /* 获取排序参数 */
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
    /**
     * mybatis_plus的page参数
     * @return
     */
    public Page getPage() {
        return new Page(this.pageNum, this.pageSize);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }

    public String getCustomValue() {
        return customValue;
    }

    public void setCustomValue(String customValue) {
        this.customValue = customValue;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public final static String  ASC= "asc";
    public final static String  DESC= "asc";
    private static final Integer PAGE_NUM = 1;
    private static final Integer PAGE_SIZE = 10;

}
