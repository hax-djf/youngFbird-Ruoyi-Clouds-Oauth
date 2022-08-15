package com.flybirds.system.sysLoginInfo.controller;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.common.core.model.UserAgentEntity;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.enums.login.AccountLoginOutChannelEnum;
import com.flybirds.common.enums.login.AccountLoginStatusEnum;
import com.flybirds.common.enums.user.UserStatusEnum;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.common.util.ip.AddressUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.system.sysLoginInfo.entity.SysLogininfor;
import com.flybirds.system.sysLoginInfo.service.ISysLogininforService;
import com.flybirds.system.sysUser.service.ISysUserService;
import com.flybirds.web.web.annotation.Login;
import com.flybirds.web.web.annotation.OauthServiceSignature;
import com.flybirds.web.web.core.util.user.UserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 系统访问记录
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/logininfor")
public class SysLogininforController extends MpBaseController
{

    @Autowired
    private ISysLogininforService logininforService;

    @Autowired
    private ISysUserService userService;

    @PreAuthorize(hasPermi = "system:logininfor:list")
    @GetMapping("/list")
    public MpTableDataInfo list(SysLogininfor logininfor)
    {
        mpStartPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getMpDataTable(list);
    }

    @GetMapping("/userList")
    @Login
    public MpTableDataInfo list()
    {
        mpStartPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(new SysLogininfor().setUserName(UserIdUtil.getUserName()));
        return getMpDataTable(list);
    }


    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "system:logininfor:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLogininfor logininfor) throws IOException
    {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        POIExcelUtil<SysLogininfor> util = new POIExcelUtil<SysLogininfor>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    @PreAuthorize(hasPermi = "system:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize(hasPermi = "system:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        logininforService.cleanLogininfor();
        return AjaxResult.success();
    }

    //用户登录或者退出 （其他日志操作）
    @PostMapping("/add/other")
    @OauthServiceSignature
    public AjaxResult add(@RequestBody UserAgentEntity userAgent,
                          @RequestParam("username") String username,
                          @RequestParam("status") Integer status,
                          @RequestParam("message") String message) {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username)
            .setIpaddr(userAgent.getIpaddr())
            .setMsg(message)
            .setBrowser(userAgent.getBrowser())
            .setOs(userAgent.getOs())
            .setLoginLocation(userAgent.getLoginLocation())
            .setLoginTime(new Date(userAgent.getLoginTime()));
        // 注销状态
        if (AccountLoginOutChannelEnum.LOGIN_OUT.getValue() == status ) {
            logininfor.setStatus(UserStatusEnum.OK.getCode());
            //用户状态处理
            SysUser sysUser = SysUser.builder()
                    .userName(username)
                    .loginIp(userAgent.getIpaddr())
                    .build();
            sysUser.setLoginIp(userAgent.getIpaddr());
            sysUser.setUpdateName(username);
            sysUser.setUpdateTime(new Date());
            userService.updateSysUserLoginByName(sysUser);

        } else if (AccountLoginStatusEnum.LOGIN_FAILURE.getValue() == status) {
            logininfor.setStatus(UserStatusEnum.ERROR.getCode());
        }
        return toAjax(logininforService.insertLogininfor(logininfor));
    }

    //登录成功日志操作
    @PostMapping("/add/login")
    @OauthServiceSignature
    public AjaxResult saveLogininforByLogin(@RequestBody UserAgentEntity userAgent,
                                            @RequestParam("username") String username,
                                            @RequestParam("status") Integer status,
                                            @RequestParam("message") String message,
                                            @RequestParam("loginChannelEnum")AccountLoginChannelEnum loginChannelEnum) {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username)
                .setIpaddr(userAgent.getIpaddr())
                .setMsg(message)
                .setBrowser(userAgent.getBrowser())
                .setOs(userAgent.getOs())
                .setLoginLocation(userAgent.getLoginLocation())
                .setLoginTime(new Date(userAgent.getLoginTime()));
        // 日志状态
        logininfor.setStatus(UserStatusEnum.OK.getCode());
        //用户状态处理
        SysUser sysUser = SysUser.builder()
                .userName(username)
                .loginIp(userAgent.getIpaddr())
                .lastLoginChannel(loginChannelEnum).build();
        sysUser.setUpdateName(username);
        sysUser.setUpdateTime(new Date());
        userService.updateSysUserLoginByName(sysUser);

        return toAjax(logininforService.insertLogininfor(logininfor));
    }

    /* 用户账号操作日志  */
    @PostMapping("/add/loginInfo")
    @OauthServiceSignature
    public AjaxResult saveLogininfor(@RequestBody LogininforRespDTO dto) {

        SysLogininfor info = new SysLogininfor();
        /** 解析用户地址信息 */
        String username = dto.getUsername();
        String status = dto.getStatus();
        String message = dto.getMessage();
        String type = dto.getType();
        UserAgentEntity userAgent = builderUerAgent(dto.getUserAgent(), dto.getUserIp());
        info.setUserName(username)
                .setIpaddr(userAgent.getIpaddr())
                .setMsg(message)
                .setBrowser(userAgent.getBrowser())
                .setOs(userAgent.getOs())
                .setStatus(status)
                .setType(type)
                .setLoginLocation(userAgent.getLoginLocation())
                .setLoginTime(new Date(userAgent.getLoginTime()));

        /** 1 登录  2 退出 {@link LogininforRespDTO#getType()} */

        switch (dto.getType()){
            case  "0" :
                if(ObjectUtil.equal(dto.getStatus(),"0")){
                    //登录成功
                    SysUser sysUser = SysUser.builder()
                            .userName(username)
                            .loginIp(userAgent.getIpaddr())
                            .lastLoginChannel(dto.getAccountLoginChannelEnum()).build();
                    sysUser.builderUpdateBaseDo(null,username);
                    userService.updateSysUserLoginByName(sysUser);
                }
                break;
            case "1":
                if(ObjectUtil.equal(dto.getStatus(),"0")){
                    //用户退出成功
                    SysUser sysUser = SysUser.builder()
                            .userName(username)
                            .loginIp(userAgent.getIpaddr())
                            .build();
                    sysUser.setLoginIp(userAgent.getIpaddr());
                    sysUser.builderUpdateBaseDo(null,username);
                    userService.updateSysUserLoginByName(sysUser);
                }
                break;
        }
        return toAjax(logininforService.insertLogininfor(info));
    }

    static UserAgentEntity builderUerAgent(String userAgent,String userIp){
        String borderName = UserAgentUtils.getBorderName(userAgent);
        String browserVersion = UserAgentUtils.getBrowserVersion(userAgent);
        String osName = UserAgentUtils.getOsName(userAgent);
        String os = UserAgentUtils.getOs(userAgent);
        return new UserAgentEntity(
                DateUtils.getNowDate().getTime(),
                userIp,
                AddressUtils.getRealAddressByIP(userIp),
                String.format("%s 版本:%s",borderName,browserVersion),
                String.format("%s 版本:%s",os,osName));
    }

}
