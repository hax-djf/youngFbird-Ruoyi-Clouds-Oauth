package com.flybirds.system.sysSocial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flybirds.common.enums.user.UserTypeEnum;
import com.flybirds.mybatis.core.basepojo.MpBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社交用户
 * 通过 {@link SysSocialUser#getUserId()} 关联到对应的 {@link com.flybirds.api.core.entity.SysUser}
 *
 * @author weir
 */
@TableName(value = "sys_social_user", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysSocialUser extends MpBaseEntity{

    /* id*/
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**关联的用户编号*/
    private Long userId;

    /**用户类型*枚举 {@link UserTypeEnum}*/
    private Integer userType;

    /**社交平台的类枚举 {@link com.flybirds.common.enums.social.SocialTypeEnum}*/
    private Integer type;

    /**社交 openid */
    private String openid;

    /* 社交 token */
    private String token;

    /**
     * 社交的全局编号
     * 例如说，微信平台的 https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html
     * 如果没有 unionId 的平台，直接使用 openid 作为该字段的值
     */
    private String unionId;

   /*原始 Token 数据，一般是 JSON 格式 */
    private String rawTokenInfo;

    /* 用户昵称 */
    private String nickname;

    /* 用户头像 */
    private String avatar;

    /* 原始用户数据，一般是 JSON 格式*/
    private String rawUserInfo;
}


