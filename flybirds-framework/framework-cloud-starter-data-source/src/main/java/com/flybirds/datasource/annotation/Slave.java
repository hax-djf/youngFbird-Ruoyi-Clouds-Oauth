package com.flybirds.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 *
 * 从数据库
 * Created by on 2021/1/28.
 * @author ruoyi
 */

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("slave")
public @interface Slave {


}
