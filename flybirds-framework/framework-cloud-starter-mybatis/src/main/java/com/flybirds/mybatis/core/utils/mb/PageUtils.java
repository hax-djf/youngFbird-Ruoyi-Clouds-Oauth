package com.flybirds.mybatis.core.utils.mb;

import com.flybirds.common.util.sql.SqlUtil;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.mybatis.core.page.MpPageDomain;
import com.flybirds.mybatis.core.page.MpTableSupport;
import com.github.pagehelper.PageHelper;

/**
 *  mybatis 分页组件
 *
 * @author :ruoyi
 */
public class PageUtils extends PageHelper {

    /**
     * 设置请求分页数据
     */
    public static void mpStartPage()
    {
        MpPageDomain maPageDomain = MpTableSupport.buildPageRequest();
        Integer pageNum = maPageDomain.getPageNum();
        Integer pageSize = maPageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(maPageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }


}
