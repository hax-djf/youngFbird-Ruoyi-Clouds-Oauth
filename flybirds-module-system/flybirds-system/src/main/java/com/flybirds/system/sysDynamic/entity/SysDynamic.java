package com.flybirds.system.sysDynamic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flybirds.mybatis.core.basepojo.MpBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 动态圈表 sys_notice
 *
 * @author flybirds
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dynamic",autoResultMap = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@Data
public class SysDynamic extends MpBaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    @TableId(value = "dynamic_id",type = IdType.AUTO)
    private Long dynamicId;

    /** 动态 */
    private String dynamicTitle;

    /** 动态类型（1私有 2公共） */
    private String dynamicType;

    /** 公告内容(文字内容)*/
    private String dynamicContent;

    /** 状态（0正常 1关闭） */
    private String status;

    /* 用户创建的头像*/
    private String avatar;

    /* 用户上传的动态图片*/
    private String imgs;

}
