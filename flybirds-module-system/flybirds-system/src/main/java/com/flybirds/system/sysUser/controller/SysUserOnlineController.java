package com.flybirds.system.sysUser.controller;

import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.UserAgentEntity;
import com.flybirds.common.enums.login.AccountLoginOutChannelEnum;
import com.flybirds.common.util.aes.AESUtil;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.security.oauth.OauthRedisManger;
import com.flybirds.security.push.OutEventPush;
import com.flybirds.security.service.UserTokenServiceManger;
import com.flybirds.system.sysUser.entity.SysUserOnline;
import com.flybirds.system.sysUser.service.ISysUserOnlineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 在线用户监控
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/online")
@Slf4j
public class SysUserOnlineController extends MpBaseController
{
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private UserTokenServiceManger userTokenService;

    @PreAuthorize(hasPermi = "monitor:online:list")
    @GetMapping("/list")
    public MpTableDataInfo list(String ipaddr, String userName)
    {
        //先写死数据
        Collection<OAuth2AccessToken> oAuth2AccessTokens = userTokenService.getTokensByClientId("flybirds");
        Set<SysUserOnline> userOnlineList = new HashSet<>();
        oAuth2AccessTokens.stream().forEach(oAuth2AccessToken -> {

            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            String user_name = (String) additionalInformation.get(SecurityConstant.DETAILS_USERNAME);
            UserAgentEntity userAgentEntity = UserTokenServiceManger.builderUerAgent(additionalInformation);

            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ipaddr, userAgentEntity.getIpaddr()) && StringUtils.equals(userName, user_name)) {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, oAuth2AccessToken,userAgentEntity,user_name));
                }
            }
            else if (StringUtils.isNotEmpty(ipaddr)) {
                if (StringUtils.equals(ipaddr,userAgentEntity.getIpaddr())) {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, oAuth2AccessToken,userAgentEntity));
                }
            }
            else if (StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(userName, user_name)) {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, oAuth2AccessToken,user_name));
                }
            }
            else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(oAuth2AccessToken));
            }

        });
        //数据去重
        List disUserOnlineList = new ArrayList(userOnlineList);
        Collections.reverse(disUserOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getMpDataTable(disUserOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize(hasPermi = "monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/forcedoutof")
    public AjaxResult forceLogout(@RequestParam String tokenSecretkey, @RequestParam(value = "userName") String userName)
    {
        AjaxResult ajaxResult = AjaxResult.success("用户强制退出成功");
        try {
            /**
             * 先解密token数据，在加密数据得到一个钥码，采用Aes算法加密数据
             */
            String userTokenToCacheKey = userTokenService.getUserTokenToCacheKey(userName, AESUtil.decrypt(tokenSecretkey, userName));
            String tokenValue = OauthRedisManger.builder().getObjectByKey(userTokenToCacheKey);
            OutEventPush.EventpushUserExit(tokenValue, AccountLoginOutChannelEnum.ADMIN_EXIT);

        }catch (RuntimeException e){
            e.printStackTrace();
            ajaxResult = AjaxResult.error("用户强制退出失败");
        }
        return ajaxResult;
    }
}
