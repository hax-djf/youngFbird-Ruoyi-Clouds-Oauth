package com.flybirds.api.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.flybirds.common.enums.login.AccountChannelEnum;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.enums.user.UserTypeEnum;
import com.flybirds.excel.annotation.ExcelPOI;
import com.flybirds.excel.annotation.ExcelPOIs;
import com.flybirds.mybatis.core.basepojo.TenantBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static com.flybirds.common.constant.Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;
import static com.flybirds.common.constant.Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;
import static com.flybirds.excel.annotation.ExcelPOI.ColumnType;
import static com.flybirds.excel.annotation.ExcelPOI.Type;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "sys_user")
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends TenantBaseEntity {

    private static final long serialVersionUID = 1L;


    @ExcelPOI(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    @TableId(value = "user_id",type = IdType.AUTO)
    @ApiModelProperty(name = "userId",value = "用户id")
    private Long userId;

    @ExcelPOI(name = "部门编号", type = Type.IMPORT)
    @ApiModelProperty(name = "deptId",value = "部门ID")
    private Long deptId;

    @ExcelPOI(name = "登录名称")
    @ApiModelProperty(name = "userName",value = "用户账号")
    private String userName;

    @ExcelPOI(name = "用户名称")
    @ApiModelProperty(name = "nickName",value = "用户昵称")
    private String nickName;

    @ExcelPOI(name = "用户邮箱")
    @ApiModelProperty(name = "email",value = "用户邮箱")
    private String email;


    @ExcelPOI(name = "手机号码")
    @ApiModelProperty(name = "phonenumber",value = "手机号码")
    private String phonenumber;

    @ExcelPOI(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty(name = "sex",value = "用户性别")
    private String sex;

    @ApiModelProperty(name = "avatar",value = "用户头像")
    private String avatar;

    @TableField(value = "pass_word")
    @ApiModelProperty(name = "passWord",value = "密码")
    private String passWord;

    @ApiModelProperty(name = "onlineSync",value = "同步在线")
    private String onlineSync;

    @ApiModelProperty(name = "onlineNumber",value = "在线数量")
    private String onlineNumber;

    @ExcelPOI(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    @TableField(value = "status")
    @ApiModelProperty(name = "status",value = "帐号状态")
    private String status;

    @ExcelPOI(name = "锁定状态", readConverterExp = "0=正常,1=锁定")
    @ApiModelProperty(name = "locks",value = " 锁定（0代表正常 1代表锁定）")
    @TableField(value = "locks")
    private String locks;

    @ApiModelProperty(name = "loginIp",value = "最后登录IP")
    @ExcelPOI(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;


    @ExcelPOI(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS,timezone = TIME_ZONE_DEFAULT_GMT)
    @ApiModelProperty(name = "loginDate",value = "最后登录时间")
    private Date loginDate;

    /** 部门对象 */
    @ExcelPOIs({
            @ExcelPOI(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @ExcelPOI(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    @TableField(value = "dept",exist = false)
    private SysDept dept;

    /** 角色对象 */
    @TableField(value = "roles",exist = false)
    private List<SysRole> roles;

    /** 角色组 */
    @TableField(value = "roleIds",exist = false)
    private Long[] roleIds;

    /** 岗位组 */
    @TableField(value = "postIds",exist = false)
    private Long[] postIds;

    /** 参数 角色id */
    @TableField(value = "roleId",exist = false)
    private Long roleId;

    @TableField(value = "reg_channel")
    @ApiModelProperty(name = "regChannel",value = "注册渠道")
    private AccountChannelEnum regChannel;

    @TableField(value = "user_type")
    @ApiModelProperty(name = "userType",value = "用户类别")
    private UserTypeEnum userType;

    @TableField(value = "last_login_channel")
    @ApiModelProperty(name = "lastLoginChannel",value = "最后登录渠道")
    private AccountLoginChannelEnum lastLoginChannel;

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName()
    {
        return nickName;
    }

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public static final String PHONENUMBER = "phonenumber";
}
