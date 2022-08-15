package com.flybirds.mybatis.core.basepojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 拓展多租户的 BaseDO 基类
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TenantBaseEntity extends MpBaseEntity {

    /**
     * 多租户编号
     */
    private Long tenantId;

}
