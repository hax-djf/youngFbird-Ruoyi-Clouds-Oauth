package com.flybirds.oauthModule.system.entity;

import cn.hutool.core.util.IdUtil;
import com.flybirds.excel.annotation.ExcelPOI;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 客户端信息对象 oauth_client_details
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Data
public class OauthClientDetails
{
    private static final long serialVersionUID = 1L;

    /** 客户端ID，主要用于标识对应的应用 */
    private String clientId;

    /** 客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: “unity-resource,mobileresource”. 该字段的值必须来源于与security.xml中标签‹oauth2:resource-server的属性resource-id值一致. 在security.xml配置有几个‹oauth2:resource-server标签, 则该字段可以使用几个值. 在实际应用中, 我们一般将资源进行分类,并分别配置对应的‹oauth2:resource-server,如订单资源配置一个‹oauth2:resource-server, 用户资源又配置一个‹oauth2:resource-server. 当注册客户端时,根据实际需要可选择资源id,也可根据不同的注册流程,赋予对应的资源id */
    @ExcelPOI(name = "客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: “unity-resource,mobileresource”. 该字段的值必须来源于与security.xml中标签‹oauth2:resource-server的属性resource-id值一致. 在security.xml配置有几个‹oauth2:resource-server标签, 则该字段可以使用几个值. 在实际应用中, 我们一般将资源进行分类,并分别配置对应的‹oauth2:resource-server,如订单资源配置一个‹oauth2:resource-server, 用户资源又配置一个‹oauth2:resource-server. 当注册客户端时,根据实际需要可选择资源id,也可根据不同的注册流程,赋予对应的资源id")
    private String resourceIds;

    /** 客户端秘钥，BCryptPasswordEncoder加密算法加密 */
    @ExcelPOI(name = "客户端秘钥，BCryptPasswordEncoder加密算法加密")
    private String clientSecret;

    /** 对应的范围 */
    @ExcelPOI(name = "对应的范围")
    private String scope;

    /** 认证模式 */
    @ExcelPOI(name = "认证模式")
    private String authorizedGrantTypes;

    /** 认证后重定向地址 */
    @ExcelPOI(name = "认证后重定向地址")
    private String webServerRedirectUri;

    /** 指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔, 如:"ROLE_*/
    @ExcelPOI(name = "指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔, 如:ROLE_")
    private String authorities;

    /** 令牌有效期 */
    @ExcelPOI(name = "令牌有效期")
    private Long accessTokenValidity;

    /** 令牌刷新周期 */
    @ExcelPOI(name = "令牌刷新周期")
    private Long refreshTokenValidity;

    /** 这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:{“country”:“CN”,“country_code”:“086”}按照spring-security-oauth项目中对该字段的描述 Additional information for this client, not need by the vanilla OAuth protocolbut might be useful, for example,for storing descriptive information. (详见ClientDetails.java的getAdditionalInformation()方法的注释)在实际应用中, 可以用该字段来存储关于客户端的一些其他信息,如客户端的国家,地区,注册时的IP地址等等.create_time数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)*/
    @ExcelPOI(name = "这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:{“country”:“CN”,“country_code”:“086”}按照spring-security-oauth项目中对该字段的描述 Additional information for this client, not need by the vanilla OAuth protocolbut might be useful, for example,for storing descriptive information. (详见ClientDetails.java的getAdditionalInformation()方法的注释)在实际应用中, 可以用该字段来存储关于客户端的一些其他信息,如客户端的国家,地区,注册时的IP地址等等.create_time数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)")
    private String additionalInformation;

    /** 设置用户是否自动Approval操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’. 该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为’true’或支持的scope值,则会跳过用户Approve的页面, 直接授权. 该字段与 trusted 有类似的功能, 是spring-security-oauth2 的 2.0 版本后添加的新属性. 在项目中,主要操作oauth_client_details表的类是JdbcClientDetailsService.java, 更多的细节请参考该类. 也可以根据实际的需要,去扩展或修改该类的实现*/
    @ExcelPOI(name = "设置用户是否自动Approval操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’. 该字段只适用于grant_type='authorization_code'的情况,当用户登录成功后,若该值为’true’或支持的scope值,则会跳过用户Approve的页面, 直接授权. 该字段与 trusted 有类似的功能, 是spring-security-oauth2 的 2.0 版本后添加的新属性. 在项目中,主要操作oauth_client_details表的类是JdbcClientDetailsService.java, 更多的细节请参考该类. 也可以根据实际的需要,去扩展或修改该类的实现'")
    private String autoapprove;

    private Date createTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("clientId", getClientId())
            .append("resourceIds", getResourceIds())
            .append("clientSecret", getClientSecret())
            .append("scope", getScope())
            .append("authorizedGrantTypes", getAuthorizedGrantTypes())
            .append("webServerRedirectUri", getWebServerRedirectUri())
            .append("authorities", getAuthorities())
            .append("accessTokenValidity", getAccessTokenValidity())
            .append("refreshTokenValidity", getRefreshTokenValidity())
            .append("additionalInformation", getAdditionalInformation())
            .append("autoapprove", getAutoapprove())
            .append("createTime", getCreateTime())
            .toString();
    }

    public static OauthClientDetails builderInit(){

        return new OauthClientDetails()
                .setClientId(new StringBuilder(IdUtil.fastSimpleUUID()).append(IdUtil.fastSimpleUUID()).toString())
                .setClientSecret(new StringBuilder(IdUtil.fastSimpleUUID()).append(IdUtil.getSnowflake(1,1).nextIdStr()).toString())
                .setCreateTime(new Date())
                .setRefreshTokenValidity(3600l)
                .setAccessTokenValidity(1800l);
    }
}
