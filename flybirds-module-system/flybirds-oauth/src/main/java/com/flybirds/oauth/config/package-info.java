/**
 * @author :flybirds
 *
 * oauth2.0的配置文件遇到的如下问题：
 * 1：需要去除 {@link com.flybirds.oauth.adapter.userDetails.UserDetailsServiceImpl#loadUserByUsername(java.lang.String)}
 *    中存在对客户端的的clientId与clientSerlert的认证
 * 原因： 原有代码中存在客户端认证的是在为了实现自定义认证登录操作中，二次封装了认证接口，但是由于认证和授权是在一块的，需要先将{@link com.flybirds.oauth.config.WebSecurityConfig}
 *       中的优先级改成-1 ，此动作会操作，在进行 {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)}
 *       获取到客户端为我们自己实现的 {@link com.flybirds.oauth.adapter.userDetails.UserDetailsServiceImpl} 因此在源代码中加入如下代码 ：代码1
 *       在oauth2.0中客户端有专用的 {@link org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService} 来进行客户端的认证
 *       因为我们应该采用默认的机制，因为我们需要适配 code授权码登录吗，模式 将原生和自定义都可以使用结合
 *
 * 2：为了去除 代码1 ，则需要将order(-1)改成order(99),但是修改以后，会导致自定义接口直接进入带授权服务器，所以为了达到自定义认证接口效果只能将授权服务器和认证服务器分开
 *
 *  【注意】：在使用原有的oauth2.0的接口的时候 如http://localhost:9001/oauth/check_token?token= [access_token],原有的将优先级调整为order(99)，可直接访问调用
 *
 * 3： 方案一【未采用】 分离认证与授权服务器 ，采用的方案为 将security中心 和 resource 分开
 *    安全中心中包括：用户的获取，用户退出之任务操作，已经远程调用的配置，权限等
 *    授授权中心应该是对于每一个小微服务的，每个微服务都需要依赖于它，进行用户授权操作
 *
 * 4: 方案二【采用】 {@link com.flybirds.oauth.config.WebSecurityConfig}优先级调整到授权服务{@link com.flybirds.security.config.ResourceServerConfig}之前
 *
 * scope核心设计：
 *
 * 关于scope的授权的基本逻辑 主要核心代码来自 {@link org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler#updateAfterApproval(org.springframework.security.oauth2.provider.AuthorizationRequest, org.springframework.security.core.Authentication)}
 * 每个的授权的值，是根据 true and false 进行权限控制的
 *
 *
 *
 *
 */
package com.flybirds.oauth.config;

// 代码1
//{
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //先进行客户端的校验操作 -取出身份，如果身份为空说明没有认证
//        if (authentication == null) { //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
//        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
//        if (clientDetails != null) {
//        //秘钥
//        String clientSecret = clientDetails.getClientSecret();
//        //数据库查找方式
//        return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
//        }
//        }
//        if (StrUtil.isBlank(username)) {
//        return null;
//        }
//        String channel = ServletUtils.getRequest().getParameter(AccountLoginChannelEnum.ACCOUNT_LOGIN_CHANNEL);
//        AssertUtil.isNotNull(channel,new CustomOauthException("AccountLoginChannel Is Null To System Error，请联系管理员！"));
//        UserLoginHandler0 service = UserLoginFactory.get0(Integer.valueOf(channel));
//}