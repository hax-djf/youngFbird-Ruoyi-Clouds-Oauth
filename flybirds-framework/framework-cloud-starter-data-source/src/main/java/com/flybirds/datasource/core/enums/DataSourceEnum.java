package com.flybirds.datasource.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对应于多数据源中不同数据源配置
 *
 * 通过在方法上，使用 {@link com.baomidou.dynamic.datasource.annotation.DS} 注解，设置使用的数据源。
 * 注意，默认是 {@link #MASTER} 数据源
 *
 * 对应官方文档为 http://dynamic-datasource.com/guide/customize/Annotation.html
 */
@Getter
@AllArgsConstructor
public enum  DataSourceEnum {

    MASTER("master","主库"), // 主库，推荐使用 {@link com.baomidou.dynamic.datasource.annotation.Master} 注解

    SLAVE("slave","c从库"); // 从库，推荐使用 {@link com.baomidou.dynamic.datasource.annotation.Slave} 注解

    private String value;

    private String label;

}
