package com.flybirds.mybatis.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.page.MpPageDomain;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.mybatis.core.page.MpTableSupport;
import com.flybirds.mybatis.core.utils.mb.PageUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理_mybatis_plus
 * 
 * @author flybirds
 */
public class MpBaseController<T> {
    protected final Logger logger = LoggerFactory.getLogger(MpBaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }
    /**
     * 参数整理
     */
    protected void initParameter(Params params){
        //初始化分页
        params.setDefaultPage();
        //排序
        params.buildOrderBy(params.getPage());
    }


    /**
     * doget请求分页封装 对于查询使用get请求 ，封装了查询排序等字段，使用的是MpPageDomain
     * @return
     */
    protected Page initPageDoGet(){
          MpPageDomain mpPageDomain = MpTableSupport.buildPageRequest();
          Integer pageNum = mpPageDomain.getPageNum();
          Integer pageSize = mpPageDomain.getPageSize();
          Page page = null;
          if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
              page = new Page(pageNum,pageSize);
              mpPageDomain.buildOrderBy(page);
          }
          return page;
    }

    /**
     * 设置请求分页数据--保留若依的原始请求
     */
    protected void mpStartPage()
    {
        PageUtils.mpStartPage();
    }

    /**
     *自定义分页数据响应
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected MpTableDataInfo getMpDataTable(IPage<T> page) {

      return  MpTableDataInfo
                .builder()
                .code(CodeConstant.Number.SUCCESS)
                .data(page.getRecords())
                .msg(MsgConstant.Business.QUERY_SUCCESS)
                .total(page.getTotal()).build();
    }

    /**
     *自定义分页数据响应
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected MpTableDataInfo getMpDataTable(List<T> list) {
        return  MpTableDataInfo
                .builder()
                .code(CodeConstant.Number.SUCCESS)
                .msg(MsgConstant.Business.QUERY_SUCCESS)
                .data(list)
                .total(new PageInfo(list).getTotal())
                .build();
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }
}
