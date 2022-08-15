package com.flybirds.mybatis.core.basequery;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 拓展 MyBatis Plus QueryWrapper 类，主要增加如下功能：
 *
 * 1. 拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。
 *
 * @param <T> 数据类型
 */
public class MpQueryWrapper<T> extends QueryWrapper<T> {

    public MpQueryWrapper<T> likeIfPresent(String column, Object obj) {
        String val = ObjectUtil.isNotNull(obj) ? String.valueOf(obj) : null;
        if (StringUtils.hasText(val)) {
            return (MpQueryWrapper<T>) super.like(column, val);
        }
        return this;
    }

    public MpQueryWrapper<T> inIfPresent(String column, Collection<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            return (MpQueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public MpQueryWrapper<T> inIfPresent(String column, Object... values) {
        if (!ArrayUtils.isEmpty(values)) {
            return (MpQueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public MpQueryWrapper<T> eqIfPresent(String column, Object val) {
        if (val != null) {
            return (MpQueryWrapper<T>) super.eq(column, val);
        }
        return this;
    }

    public MpQueryWrapper<T> neIfPresent(String column, Object val) {
        if (val != null) {
            return (MpQueryWrapper<T>) super.ne(column, val);
        }
        return this;
    }

    public MpQueryWrapper<T> gtIfPresent(String column, Object val) {
        if (val != null) {
            return (MpQueryWrapper<T>) super.gt(column, val);
        }
        return this;
    }

    public MpQueryWrapper<T> geIfPresent(String column, Object val) {
        if (val != null) {
            return (MpQueryWrapper<T>) super.ge(column, val);
        }
        return this;
    }

    public MpQueryWrapper<T> ltIfPresent(String column, Object val) {
        if (val != null) {
            return (MpQueryWrapper<T>) super.lt(column, val);
        }
        return this;
    }

    public MpQueryWrapper<T> leIfPresent(String column, Object val) {
        if (val != null) {
            return (MpQueryWrapper<T>) super.le(column, val);
        }
        return this;
    }

    public MpQueryWrapper<T> betweenIfPresent(String column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (MpQueryWrapper<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (MpQueryWrapper<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (MpQueryWrapper<T>) le(column, val2);
        }
        return this;
    }

    // ========== 重写父类方法，方便链式调用 ==========

    @Override
    public MpQueryWrapper<T> eq(boolean condition, String column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public MpQueryWrapper<T> eq(String column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public MpQueryWrapper<T> orderByDesc(String column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public MpQueryWrapper<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public MpQueryWrapper<T> in(String column, Collection<?> coll) {
        super.in(column, coll);
        return this;
    }

}
