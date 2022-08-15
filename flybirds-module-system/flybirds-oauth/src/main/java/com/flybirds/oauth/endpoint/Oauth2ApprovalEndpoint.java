package com.flybirds.oauth.endpoint;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.flybirds.common.enums.login.AccountAuthorizationScopeEnum;
import com.flybirds.security.core.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 授权确认页面 重写 {@link WhitelabelApprovalEndpoint#getAccessConfirmation(Map, HttpServletRequest)}
 *
 * 主要授权处理在 {@link AuthorizationEndpoint}
 * @author flybirds
 */
@Controller
@SessionAttributes("authorizationRequest")
@RequestMapping("/oauth")
public class Oauth2ApprovalEndpoint {

    @RequestMapping("/confirmAccess")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("oauth-grant"); //自定义页面名字，resources\templates\code-grant.html
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes", AccountAuthorizationScopeEnum.get(authorizationRequest.getScope()));
        view.addObject("redirect_uri",authorizationRequest.getRedirectUri());
        view.addObject("response_type",authorizationRequest.getResponseTypes());
        view.addObject("userInfo",SecurityUtils.getLoginUser());
        return view;
    }

    @ApiOperation(value = "处理授权异常的跳转页面")
    @GetMapping("/error")
    public String error(Model model){
        return "oauth-error";
    }
}

