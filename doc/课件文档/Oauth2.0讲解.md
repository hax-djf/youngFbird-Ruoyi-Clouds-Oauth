**YOU课大赛参赛主题**

<img src="C:\Users\米饭饭一族\Desktop\hax\神州数码\yong比赛\oauth-logo.png" alt="oauth-logo" style="zoom: 50%;" />

# SpringCloud & Alibaba+Oauth2.0认证与授权

##  个人介绍 

 **姓名**：胡奥雄

 **职位**：金融SBG.政企BG Java开发工程师

## 课程内容

- 1、认证授权的分析 & 市场上解决技术方案
- 2、Oauth2.0 介绍
- 3、SpringSecurity Oauth2.0介绍分析
  - springBoot集成Oauth2.0的Demo实现
    - Oauth2.0授权服务搭建 & 认证模式
    - Oauth2.0资源服务搭建 & 资源访问
- 4、SpringCloud & Alibaba+Oauth2.0实现SSO
  - 网关gateway & 注册配置中心Nacos & 熔断限流Sentinel 
  - 系统微服务模块 system & 短信微服务模块sms &  oss对象存储微服务模块 & monitor安全监控微服务模块 &  自定义组件components 介绍说明 
  - **spring-cloud-starter-oauth2** 安全中心组件 & **Oauth2.0授权微服务模块**
    -    自定义配置讲解
    -    disruptor ( 高性能队列 ) 、redis & sub/pub (订阅发布模式队列)
  - Oauth2.0原生授权接口 & **Oauth2.0自定义适配授权接口**
    - ​	短信验证码登录
    - ​    社交登录（Gitee）
    - ​    社交绑定登录（Gitee）

## 1、认证授权的分析

### 1.1认证授权的基本概念

**1、认证概念**

​	系统为什么要认证？

​	认证是为了保护系统的隐私数据与资源，访问的用户是否合法的获取系统的数据与资源。

​	用户身份认证即用户去访问系统资源时系统要求验证用户的身份信息，身份合法方可继续访问。常见的用户身	份认证表现形式有：用户名密码登录，短信验证码登录，二维码登录，**第三方授权登录等方式**。总的来说，就	相当于校验用户账号密码是否正确；

**2、授权概念**

​	系统为什么要授权？

​	认证是体现身份的合法性，授权则是更加细粒度对数据权限进行划分，授权是在认证之后发生的，不同的用户	有着不同的权限资源，例如某视的vip可以看1080P,而普通用户只能高清，甚至不能看，必须开Vip；

​	案例参考地址：[链接地址](https://gitee.com/oauth/authorize?client_id=f535ccad2b42f8d7a5b44aa50296810bb77eb1bcf69274533961757699a96795&redirect_uri=http%3A%2F%2Flocalhost%2Fsocial-login%3Ftype%3D10&response_type=code)

​	用户认证通过后去访问系统的资源，系统会判断用户是否拥有访问资源的权限，只允许访问有权限的系统资	  	源，没有权限的资源将无法访问，这个过程叫用户授权；

**3、会话概念**

​	用户认证成功之后，为了避免每次访问都需要进行认证的动作，一般会将用户的信息保存在当前的会话中，简	单的理解会话，会话就是提供用户在某个系统中的登录状态机制，常见方案如下：

- **session方式**

  - 大家都知道cookie 是有浏览器提供的，每次进行服务端访问的的时候，都会去携带cookie中存在的一个 JSESSIONID ，正是因为如此我们就可以通过这种机制来实现用户会话过程；

  - 用户认证成功后，在服务端生成用户相关的数据保存在session(当前会话)中，发给客户端的 sesssion_id 在 cookie 中，这样用户客户端请求时带上 session_id 就可以验证服务器端是否存在 session 数据，以此完成用户的合法校验，当用户退出系统或session过期销毁时,客户端的session_id也就无效了；

- **token方式**

  - token是当前主流的一种会话机制，因为token不占用什么系统内存，而且还可以直接结合**Jwt**进行用户信息存着，与服务端保持一种无状态的机制，不占用内存资源

  - 用户认证成功后，服务端生成一个token发给客户端，客户端可以放到 cookie 或 localStorage 等存储中，每次请求时带上 token，服务端收到token通过验证后即可确认用户身份。

    ```javascript
     headers: {
        'Authorization': 'Bearer ' + token
     }
    ```

- **两者之间比较**
  
  - 基于session的认证方式由Servlet规范定制，服务端要存储session信息需要占用内存资源，客户端需要支持 cookie；基于token的方式则一般不需要服务端存储token，并且不限制客户端的存储方式。如今移动互联网时代 更多类型的客户端需要接入系统，系统多是采用前后端分离的架构进行实现，所以基于token的方式更适合；

------

### 1.2 第三方登录

####  1.2.1 第三方登录介绍

​	现在已经是2022年，第三方登录在生活中太常见不过了，而且有特别重要的一点是，现在的App太多了，如果	每一个App的用户只能关联自己系统本身的话，估计你的记事本得暴，所有对于很多大型的社交APP，或者支付	商都有第三方登录的API对接，这样的话，用户不需要注册就可以直接实现登录；

####  1.2.2第三方认证图解

​	腾讯云登录，就可以使用第三方登录方式

​	参考地址：[链接地址](https://cloud.tencent.com/login?s_url=https%3A%2F%2Fconsole.cloud.tencent.com%2Flighthouse%2Finstance%2Findex)

![1658931450795](C:\Users\米饭饭一族\AppData\Roaming\Typora\typora-user-images\1658931450795.png)	

------

### 1.3 SSO

#### 1.3.1单点登录

​	百度百科：单点登录（Single Sign On），简称为 SSO，是目前比较流行的企业业务整合的解决方案之一,SSO的	定义是在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统 ;

​	对于一个单体的系统很少会考虑到单点登录的情况，因为单体的系统只存在一个系统，用户认证完成之后就可	以进行授权了，但是在微服务中，会涉及到很多子系统的之间的访问，并且各个系统的资源也是需要对应的权	限的，如果用户每次访问一个子系统都需要进行认证的话，那就太麻烦了，所有我们需要实现的动作就是用户	在某一个系统中实现了认证，其他任意的子服务都可以访问，这种功能就叫单点登录；

#### 1.3.2技术实现

​	市面上比较主流如下：

- Apache Shiro

- CAS 是 耶鲁大学（Yale University）发起的一个开源项目，旨在为 Web 应用系统提供一种可靠的单点登录方法

  - [文章地址](https://zhuanlan.zhihu.com/p/66037342)

   ![preview](https://pic2.zhimg.com/v2-8ce6dc1fdf9b8eb279292acfa2f6f6ed_r.jpg) 

   ![img](https://pic3.zhimg.com/80/v2-9e07942808b0e245ae915e31fd780012_720w.jpg) 

- Spring security Oauth2.0 

  

## 2、 Oauth2.0 介绍分析

### 2.1 Oauth2.0是什么？

​	百度百科：OAuth（开放授权）是一个开放标准，允许用户授权第三方移动应用访问他们存储在另外的服务提	供者上的信息，而不需要将用户名和密码提供给第三方移动应用或分享他们数据的所有内容OAuth2.0   OAuth	协议的延续版本

​	Oauth2.0是业界内认证与授权的标准，同时也是一项技术；

------

### 2.2 Oauth2.0认证过程

​	参考地址：[链接地址](https://cloud.tencent.com/login?s_url=https%3A%2F%2Fconsole.cloud.tencent.com%2Flighthouse%2Finstance%2Findex)

![Oauth2.0授权图](C:\Users\米饭饭一族\Desktop\hax\神州数码\yong比赛\Oauth2.0授权图.jpg)

- 1、**客户端请求第三方授权** 

  用户进入腾讯云程序的登录页面，点击QQ的图标以QQ账号登录系统，用户是自己在QQ里信息的资源拥有者

- 2、**资源拥有者同意给客户端授权** 

  资源拥有者输入账号密码表示资源拥有者同意给客户端授权，QQ会对资源拥有者的身份进行验证， 验证通过后，QQ会询问用户是否给授权腾讯云访问自己的QQ数据，用户点击“确认登录”表示同意授权，QQ认证服务器会 颁发一个授权码，并重定向到腾讯云的网站。

- 3、**客户端获取到授权码，请求认证服务器申请令牌** 

  此过程用户看不到，客户端应用程序请求认证服务器，请求携带授权码。 

- 4、**认证服务器向客户端响应令牌** 

  QQ认证服务器验证了客户端请求的授权码，如果合法则给客户端颁发令牌，令牌是客户端访问资源的通行证。 此交互过程用户看不到，当客户端拿到令牌后，用户在腾讯云看到已经登录成功。 

- 5、**客户端请求资源服务器的资源** 

  客户端携带令牌访问资源服务器的资源。 

  腾讯云网站携带令牌请求访问QQ服务器获取用户的基本信息。 

- 6、**资源服务器返回受保护资源** 

  资源服务器校验令牌的合法性，如果合法则向用户响应资源信息内容。

------

### 2.3 Oauth2.0中4个重要的角色

​	OAauth2.0包括以下角色： 

- 1、**客户端** 

  本身不存储资源，需要通过资源拥有者的授权去请求资源服务器的资源，比如：Android客户端、Web客户端（浏览器端）、腾讯云客户端等。 

- 2、**资源拥有者** 

  通常为用户，也可以是应用程序，即该资源的拥有者。 

- 3、**授权服务器**（也称认证服务器）

- 4、**资源服务器** 

  存储资源的服务器，本例子为QQ存储的用户信息。 

  值得注意一点

  服务提供商能允许随便一个**客户端**就接入到它的**授权服务器**吗？答案是否定的，服务提供商会 给准入的接入方一个身份，用于接入时的凭据: **client_id**：客户端标识 **client_secret**：客户端秘钥 

  因此，授权服务器**对两种OAuth2.0中的两个角色进行认证授权，分别是**资源拥有者**、**客户端。

------

## 3、Spring  Security OAuth2

### 3.1 Spring  Security OAuth2 中授权服务与资源服务

1、**授权服务** (AuthorizationServer，也叫做认证服务) 

```java

public interface AuthorizationServerConfigurer {
     void configure(ClientDetailsServiceConfigurer var1) throws Exception;
     void configure(AuthorizationServerSecurityConfigurer var1) throws Exception;
     void configure(AuthorizationServerEndpointsConfigurer var1) throws Exception;
}
```

2、**资源服务** (ResourceServer)

```java
public interface ResourceServerConfigurer {
    void configure(ResourceServerSecurityConfigurer var1) throws Exception;
    void configure(HttpSecurity var1) throws Exception;
}
```

3、端点认证授权：

- **AuthorizationEndpoint** 
  - 服务于认证请求。默认 URL： /oauth/authorize 。 
- **TokenEndpoint** 
  - 服务于访问令牌的请求。默认 URL： /oauth/token 。
  - 认证的基本流程图如下：

![1658998533370](C:\Users\米饭饭一族\AppData\Roaming\Typora\typora-user-images\1658998533370.png)



------

### 3.2 SpringBoot + SpringSecurity OAuth2 案例工程

##### 前言：

看看springColud的更新日志

https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-2020.0-Release-Notes

![1659365904968](C:\Users\米饭饭一族\AppData\Roaming\Typora\typora-user-images\1659365904968.png)

地址： https://spring.io/projects/spring-authorization-server

![1659365765698](C:\Users\米饭饭一族\AppData\Roaming\Typora\typora-user-images\1659365765698.png)

#### 3.2.1 授权服务器的配置介绍

`@EnableAuthorizationServer `注解并继承AuthorizationServerConfigurerAdapter来配置OAuth2.0 授权 服务器 

```java
public class AuthorizationServerConfigurerAdapter implements AuthorizationServerConfigurer {
public AuthorizationServerConfigurerAdapter() {}
public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {}
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {}
public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {}
}
```

我们打开`AuthorizationServerConfigurerAdapter`的这个类的具体定义，发现了其实都是进行服务配置对象，他们都会被 Spring传入`AuthorizationServerConfigurer `进行配置

这些配置类的具体的作用是干什么的？

- **AuthorizationServerSecurityConfigurer**   

  用来配置令牌端点(Token Endpoint)的安全约束 ,  继承SecurityConfigurerAdapter.也就是一个 Spring Security安全配置提供给AuthorizationServer去配置AuthorizationServer的端点（/oauth/**）的安全访问规则、过滤器Filter 

  源码解析：

  ```java
  public final class AuthorizationServerSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> 
  {
      private AuthenticationEntryPoint authenticationEntryPoint;
      private AccessDeniedHandler accessDeniedHandler = new OAuth2AccessDeniedHandler();
      private PasswordEncoder passwordEncoder; // client secrets加密机制
      private String realm = "oauth2/client";
      private boolean allowFormAuthenticationForClients = false;
      private String tokenKeyAccess = "denyAll()";
      private String checkTokenAccess = "denyAll()";
      private boolean sslOnly = false;
      // 开发定义过滤器
      private List<Filter> tokenEndpointAuthenticationFilters = new ArrayList();
      private List<AuthenticationProvider> authenticationProviders = new ArrayList();
      private AuthenticationEventPublisher authenticationEventPublisher;
  
      public void init(HttpSecurity http) throws Exception {
          // 异常配置处理
          this.registerDefaultAuthenticationEntryPoint(http);
          // 声明了一个 AuthenticationManagerBuilder 配置类，其中包含了 UserDetailsService的实现配置
          AuthenticationManagerBuilder builder = (AuthenticationManagerBuilder)http.getSharedObject(AuthenticationManagerBuilder.class);
          if(this.authenticationEventPublisher != null) {
              builder.authenticationEventPublisher(this.authenticationEventPublisher);
          }
  
          if(this.authenticationProviders.isEmpty()) {
              // passwordEncoder注入到ClientDetailsUserDetailsService
              if(this.passwordEncoder != null) {
                  builder.userDetailsService(new ClientDetailsUserDetailsService(this.clientDetailsService())).passwordEncoder(this.passwordEncoder());
              } else {
                  builder.userDetailsService(new ClientDetailsUserDetailsService(this.clientDetailsService()));
              }
          } else {
              Iterator var3 = this.authenticationProviders.iterator();
  
              while(var3.hasNext()) {
                  AuthenticationProvider provider = (AuthenticationProvider)var3.next();
                  builder.authenticationProvider(provider);
              }
          }
          //2.配置/oaut/***端点 httpBasic安全规则
          ((HttpSecurity)((HttpSecurity)http.securityContext().securityContextRepository(new NullSecurityContextRepository()).and()).csrf().disable()).httpBasic().authenticationEntryPoint(this.authenticationEntryPoint).realmName(this.realm);
          //3. ssl 通道安全
          if(this.sslOnly) {
              ((RequiresChannelUrl)http.requiresChannel().anyRequest()).requiresSecure();
          }
  
      }
  
      private PasswordEncoder passwordEncoder() {
          return new PasswordEncoder() {
              public boolean matches(CharSequence rawPassword, String encodedPassword) {
                  return StringUtils.hasText(encodedPassword)?AuthorizationServerSecurityConfigurer.this.passwordEncoder.matches(rawPassword, encodedPassword):true;
              }
  
              public String encode(CharSequence rawPassword) {
                  return AuthorizationServerSecurityConfigurer.this.passwordEncoder.encode(rawPassword);
              }
          };
      }
      /**
       * 配置异常发生时入口点AuthenticationEntryPoint
       * @param http
       */
      private void registerDefaultAuthenticationEntryPoint(HttpSecurity http) {
          ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling = (ExceptionHandlingConfigurer)http.getConfigurer(ExceptionHandlingConfigurer.class);
          if(exceptionHandling != null) {
               //1.端点入口：BasicAuthenticationEntryPoint设置response配置。
              // response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\""); 	//response.sendError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase())
              if(this.authenticationEntryPoint == null) {
                  BasicAuthenticationEntryPoint basicEntryPoint = new BasicAuthenticationEntryPoint();
                  basicEntryPoint.setRealmName(this.realm);
                  this.authenticationEntryPoint = basicEntryPoint;
              }
  
              ContentNegotiationStrategy contentNegotiationStrategy = (ContentNegotiationStrategy)http.getSharedObject(ContentNegotiationStrategy.class);
              if(contentNegotiationStrategy == null) {
                  contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
              }
  
              MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher((ContentNegotiationStrategy)contentNegotiationStrategy, new MediaType[]{MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_XML, MediaType.MULTIPART_FORM_DATA, MediaType.TEXT_XML});
              preferredMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
  //发生发生异常时，会调用到BasicAuthenticationEntryPoint.commence()         
              exceptionHandling.defaultAuthenticationEntryPointFor((AuthenticationEntryPoint)this.postProcess(this.authenticationEntryPoint), preferredMatcher);
          }
      }
      
      /**
       * 开发配置：
       * 1. allowFormAuthenticationForClients 允许表单认证。针对/oauth/token端点添加ClientCredentialsTokenEndpointFilter
       * 2. 添加开发配置tokenEndpointAuthenticationFilters。（在 BasicAuthenticationFilter之前）
       * 3. 添加在 accessDeniedHandler
       */
      public void configure(HttpSecurity http) throws Exception {
          this.frameworkEndpointHandlerMapping();
          //针对/oauth/token端点添加ClientCredentialsTokenEndpointFilter
          if(this.allowFormAuthenticationForClients) {
              this.clientCredentialsTokenEndpointFilter(http);
          }
  
          Iterator var2 = this.tokenEndpointAuthenticationFilters.iterator();
  
          //3.添加开发配置tokenEndpointAuthenticationFilters。（在 BasicAuthenticationFilter之前）
          while(var2.hasNext()) {
              Filter filter = (Filter)var2.next();
              http.addFilterBefore(filter, BasicAuthenticationFilter.class);
          }
  
          http.exceptionHandling().accessDeniedHandler(this.accessDeniedHandler);
      }
  	
      /**
       * 核心：
       * "/oauth/token"端点的添加过滤器clientCredentialsTokenEndpointFilter（clientId, client_secert）密码比对。而且
       *  放到BasicAuthenticationFilter之前。
       *  http.addFilterBefore(clientCredentialsTokenEndpointFilter, BasicAuthenticationFilter.class);
       */
      private ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter(HttpSecurity http) {
          ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter = new ClientCredentialsTokenEndpointFilter(this.frameworkEndpointHandlerMapping().getServletPath("/oauth/token"));
          clientCredentialsTokenEndpointFilter.setAuthenticationManager((AuthenticationManager)http.getSharedObject(AuthenticationManager.class));
          OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
          authenticationEntryPoint.setTypeName("Form");
          authenticationEntryPoint.setRealmName(this.realm);
          clientCredentialsTokenEndpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint);
          clientCredentialsTokenEndpointFilter = (ClientCredentialsTokenEndpointFilter)this.postProcess(clientCredentialsTokenEndpointFilter);
          http.addFilterBefore(clientCredentialsTokenEndpointFilter, BasicAuthenticationFilter.class);
          return clientCredentialsTokenEndpointFilter;
      }
  
      private ClientDetailsService clientDetailsService() {
          return (ClientDetailsService)((HttpSecurity)this.getBuilder()).getSharedObject(ClientDetailsService.class);
      }
  
      private FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping() {
          return (FrameworkEndpointHandlerMapping)((HttpSecurity)this.getBuilder()).getSharedObject(FrameworkEndpointHandlerMapping.class);
      }
  
      public void addTokenEndpointAuthenticationFilter(Filter filter) {
          this.tokenEndpointAuthenticationFilters.add(filter);
      }
  
      public void tokenEndpointAuthenticationFilters(List<Filter> filters) {
          Assert.notNull(filters, "Custom authentication filter list must not be null");
          this.tokenEndpointAuthenticationFilters = new ArrayList(filters);
      }
  }
  ```

- **ClientDetailsServiceConfigurer**

  `ClientDetailsServiceConfigurer` 主要是注入`ClientDetailsService`实例对象(`AuthorizationServerConfigurer` 的一个回调配置项，唯一配置注入) ，能够使用内存或者JDBC来实现客户端详情服务（`ClientDetailsService`），系统提供的两个`ClientDetailsService`实现类：`JdbcClientDetailsService`、`InMemoryClientDetailsService`(默认)

- **AuthorizationServerEndpointsConfigurer** 

   `AuthorizationServerEndpointsConfigurer` 其实是一个装载类，装载Endpoints所有相关的类配置（AuthorizationServer、TokenServices、TokenStore、ClientDetailsService、UserDetailsService）。

  也就是说进行密码验证的一些工具类或服务类，均在这个地方进行注入，例如UserDetailsService，我们知道UserDetailsService是负责从数据库读取用户数据的，用户数据包含密码信息，这样，框架就可以判断前端传入的用户名和密码是否正确。

  如果启用token校验的话，就需要注入TokenServices，TokenStore是对token的额外补充，用来确定token存储的。

  - jwtAccessTokenConverter转换器用于来配置令牌， 是用来生成token的转换器，而token令牌默认是有签名的，且资源服务器需要验证这个签名。此处的加密及验签包括两种方式 
    - 对称加密
    - 非对称加密（RSA）公钥，私钥

- **AuthenticationManager**

  ```java
  public interface AuthenticationManager {    
  Authentication authenticate(Authentication var1) throws AuthenticationException;
  }
  ```

  - AuthenticationManager是一个用来处理认证（Authentication）请求的接口。在其中只定义了一个方法		authenticate()，该方法只接收一个代表认证请求的Authentication对象作为参数，如果认证成功，则会	返回一个封装了当前用户权限等信息的Authentication对象进行返回;

  - Spring Security中，AuthenticationManager的默认实现是ProviderManager，而且它不直接自己处理认证请求，而是委托给其所配置的AuthenticationProvider列表，然后会依次使用每一个AuthenticationProvider进行认证;

  - 如果有一个AuthenticationProvider认证后的结果不为null，则表示该AuthenticationProvider已经	认证成功，之后的AuthenticationProvider将不再继续认证。然后直接以该AuthenticationProvider的认证结果作为ProviderManager的认证结果。

  - 如果所有的AuthenticationProvider的认证结果都为null，则表示认证失败，将抛出一个ProviderNotFoundException
  - Spring Security内部的DaoAuthenticationProvider就是使用的这种方式。其内部使UserDetailsService来负责加载UserDetails。在认证成功以后会使用加载的UserDetails来封装要返回的Authentication对象，加载的UserDetails对象是包含用户权限等信息的。认证成功返回的Authentication对象将会保存在当前的SecurityContext中;

- **UserDetailsService**

  ```java
  public interface UserDetailsService {
      UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;
  }
  ```

#### 3.2.2 四种授权模式

​	在说四种授权模式，上面有聊到一个BasicAuthenticationFilter的过滤器，其实就是对

​	此链接需要使用 http Basic认证。 什么是http Basic认证？ http协议定义的一种认证方式，将客户端id和客户端	密码按照“客户端ID:客户端密码”的格式拼接，并用base64编 码，放在header中请求服务端；

​	一个例子：Authorization：Basic WGNXZWJBcHA6WGNXZWJBcHA=WGNXZWJBcHA6WGNXZWJBcHA= 是用	户名:密码的base64编码。 认证失败服务端返回 401 Unauthorized。

- **授权码模式（authorization code）**

  授权码模式（authorization code）是功能最完整、流程最严密的授权模式。它的特点就是通过客户端的后台服务器，与"服务提供商"的认证服务器进行互动 

  ![授权码流程](C:\Users\米饭饭一族\Desktop\hax\神州数码\yong比赛\授权码流程.png)

  - 步骤

    - （A）用户访问客户端，后者将前者导向认证服务器。

    - （B）用户选择是否给予客户端授权。

    - （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。

    - （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。

    - （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）

  - 参数

    - `response_type`：表示授权类型，必选项，此处的值固定为"code"

    - `client_id`：表示客户端的ID，必选项

    - `redirect_uri`：表示重定向URI，可选项

    - `scope`：表示申请的权限范围，可选项

    - `state`：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值

  - API

    ```http
    # 授权获取code
    http://localhost:59901/oauth/authorize?client_id=dcits-client-id&response_type=code&redirect_uri=http%3A%2F%2Fwww.baidu.com
    # 获取token
    localhost:59901/oauth/token?code=5__YxU&client_id=dcits-client-id&client_secret=dcits-client-secret&grant_type=authorization_code&redirect_uri=http%3A%2F%2Fwww.baidu.com
    ```

- **简化模式（implicit）**

  简化模式（implicit grant type）不通过第三方应用程序的服务器，直接在浏览器中向认证服务器申请令牌，跳过了"授权码"这个步骤，因此得名。所有步骤在浏览器中完成，令牌对访问者是可见的，且客户端不需要认证。

   ![简化模式流程](C:\Users\米饭饭一族\Desktop\hax\神州数码\yong比赛\简化模式流程.png)

  - 步骤

    - （A）客户端将用户导向认证服务器。

    - （B）用户决定是否给于客户端授权。

    - （C）假设用户给予授权，认证服务器将用户导向客户端指定的"重定向URI"，并在URI的Hash部分包含了访问令牌。

    - （D）浏览器向资源服务器发出请求，其中不包括上一步收到的Hash值。

    - （E）资源服务器返回一个网页，其中包含的代码可以获取Hash值中的令牌。

    - （F）浏览器执行上一步获得的脚本，提取出令牌。

    - （G）浏览器将令牌发给客户端。

  - 参数

    - `response_type`：表示授权类型，此处的值固定为"token"，必选项。

    - `client_id`：表示客户端的ID，必选项。

    - `redirect_uri`：表示重定向的URI，可选项。

    - `scope`：表示权限范围，可选项。

    - `state`：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值

  - API

    ```http
    # 获取token 
    http://localhost:59901/oauth/authorize?client_id=dcits-client-id&response_type=token&redirect_uri=http%3A%2F%2Fwww.baidu.com
    ```

- **密码模式（password）**

  密码模式（Resource Owner Password Credentials Grant）中，用户向客户端提供自己的用户名和密码。客户端使用这些信息，向"服务商提供商"索要授权。

  在这种模式中，用户必须把自己的密码给客户端，但是客户端不得储存密码。这通常用在用户对客户端高度信任的情况下，比如客户端是操作系统的一部分，或者由一个著名公司出品。而认证服务器只有在其他授权模式无法执行的情况下，才能考虑使用这种模式。

  ![密码模式流程](C:\Users\米饭饭一族\Desktop\hax\神州数码\yong比赛\密码模式流程.png)

  - 步骤

    - （A）用户向客户端提供用户名和密码。

    - （B）客户端将用户名和密码发给认证服务器，向后者请求令牌。

    - （C）认证服务器确认无误后，向客户端提供访问令牌。

  - 参数

    - `grant_type`：表示授权类型，此处的值固定为"password"，必选项。

    - `username`：表示用户名，必选项。

    - `password`：表示用户的密码，必选项。

    - `scope`：表示权限范围，可选项。

  - API

    ```http
    # 获取token
    localhost:59901/oauth/token?username=admin&password=123456&client_id=dcits-client-id&client_secret=dcits-client-secret&grant_type=password&redirect_uri=http%3A%2F%2Fwww.baidu.com
    ```
  
- **客户端模式（ client credentials ）**

  客户端模式（Client Credentials Grant）指客户端以自己的名义，而不是以用户的名义，向"服务提供商"进行认证。严格地说，客户端模式并不属于OAuth框架所要解决的问题。在这种模式中，用户直接向客户端注册，客户端以自己的名义要求"服务提供商"提供服务，其实不存在授权问题。 

  ![客户端模式](C:\Users\米饭饭一族\Desktop\hax\神州数码\yong比赛\客户端模式.png)

  - 步骤

    - （A）客户端向认证服务器进行身份认证，并要求一个访问令牌。

    - （B）认证服务器确认无误后，向客户端提供访问令牌。

  - 参数
    - `granttype`：表示授权类型，此处的值固定为"clientcredentials"，必选项。
    - `scope`：表示权限范围，可选项
    
  - API

    ```http
    #获取token
    localhost:59901/oauth/token?client_id=dcits-client-id&client_secret=dcits-client-secret&grant_type=client_credentials&redirect_uri=http%3A%2F%2Fwww.baidu.com
    ```

- **刷新模式 （refresh_token）**

  如果用户访问的时候，客户端的"访问令牌"已经过期，则需要使用"更新令牌"申请一个新的访问令牌。

  客户端发出更新令牌的HTTP请求

  - 参数
    - `granttype`：表示使用的授权模式，此处的值固定为"refreshtoken"，必选项。
    - ·refresh_token`：表示早前收到的更新令牌，必选项。
    - `scope`：表示申请的授权范围，不可以超出上一次申请的范围，如果省略该参数，则表示与上一次一致。

  - API

    ```http
    # 获取token
    localhost:59901/oauth/token?client_id=dcits-client-id&client_secret=dcits-client-secret&grant_type=refresh_token&redirect_uri=http%3A%2F%2Fwww.baidu.com&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJDbUpkalk1VUFpNjNhU0VFLUFjelp1SFp2ckEiLCJleHAiOjE2NTk1MDYzNDYsImF1dGhvcml0aWVzIjpbInN5czp1c2VyOmFkZCIsInN5czp1c2VyOmVkaXQiXSwianRpIjoic0VVNzJ0REhfTlhDc01pby1XZzhnRmxBbldvIiwiY2xpZW50X2lkIjoiZGNpdHMtY2xpZW50LWlkIn0.PmUmfVR7sbmG_A83DS9BKvADzJxqIE5P_xIFiqnzV28
    ```



#### 3.2.3 资源服务器的配置介绍

 @EnableResourceServer 注解到一个 @Configuration 配置类上，并且必须使用 ResourceServerConfigurer 	 这个 配置对象来进行配置 ,通常是使用 extend ResourceServerConfigurerAdapter，进行方法的覆盖；

```Java
//ResourceServerConfigurerAdapter适配
public class ResourceServerConfigurerAdapter implements ResourceServerConfigurer {
    public ResourceServerConfigurerAdapter() {
    }

    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    }

    public void configure(HttpSecurity http) throws Exception {
        ((AuthorizedUrl)http.authorizeRequests().anyRequest()).authenticated();
    }
}
// 实现
@Configuration
@EnableResourceServer
public class ResouceServerConfig extends ResourceServerConfigurerAdapter{
	// todo
}
```

这些配置类的具体的作用是干什么的？

-  tokenServices：ResourceServerTokenServices 类的实例，用来实现令牌服务。
-  tokenStore：TokenStore类的实例，指定令牌如何访问，与tokenServices配置可选 
- resourceId：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。 
- 其他的拓展属性例如 tokenExtractor 令牌提取器用来提取请求中的令牌。  

 HttpSecurity配置这个与Spring Security类似： 

- 请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是保护资源服务的全部路径。

-  通过http.authorizeRequests()来设置受保护资源的访问规则 

- 其他的自定义权限保护规则通过 HttpSecurity 来进行配置 

 @EnableResourceServer 注解自动增加了一个类型为 OAuth2AuthenticationProcessingFilter 的过滤器链

#### 3.2.4 flybirds-boot-starter-oss工程搭建

基本代码项目

```xml
 	<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.3</version>
	</parent>
	<groupId>com.flybirds</groupId>
        <artifactId>flybirds-boot-starter-oss</artifactId>
        <version>1.0-SNAPSHOT</version>
	<packaging>pom</packaging>

    <properties>
        <spring-boot.version>2.3.5.RELEASE</spring-boot.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>2020.0.5</spring-cloud.version>
    </properties>

	<dependencyManagement>
        <dependencies>
            <!-- SpringCloud 微服务 -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>javax.servlet</groupId>
                <artifactId>javax.servlet-api</artifactId>
                <version>3.1.0</version>
                <scope>provided</scope>
            </dependency>

            <dependency>
                <groupId>javax.interceptor</groupId>
                <artifactId>javax.interceptor-api</artifactId>
                <version>1.2</version>
            </dependency>

            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>1.2.47</version>
            </dependency>

            <dependency>
                <groupId>org.springframework.security</groupId>
                <artifactId>spring-security-jwt</artifactId>
                <version>1.0.10.RELEASE</version>
            </dependency>

        </dependencies>
    </dependencyManagement>

    <build>
        <finalName>${project.name}</finalName>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>**/*</include>
                </includes>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>

            <plugin>
                <artifactId>maven-resources-plugin</artifactId>
                <configuration>
                    <encoding>utf-8</encoding>
                    <useDefaultDelimiters>true</useDefaultDelimiters>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

```xml
 <properties>
        <hutool.version>5.6.1</hutool.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>${hutool.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.security.oauth.boot</groupId>
            <artifactId>spring-security-oauth2-autoconfigure</artifactId>
            <version>2.4.3</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.0</version>
        </dependency>
    </dependencies>
```

yml配置

```yml
server:
  port: 59901

spring:
  main:
    allow-bean-definition-overriding: true
  application:
    name: oauth2-oss
```

`AuthorizationServer.java`

```java
@Configuration
@EnableAuthorizationServer // 开始授权服务
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private String SIGNING_KEY = "dcits-123";

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //密码机制
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        //使用内存存储令牌（普通令牌）
        return new JwtTokenStore(jwtAccessTokenConverter()); //使用jwt生成token
    }

    //客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory()// 使用in-memory存储 内存存储
                .withClient("dcits-client-id")// client_id
                .secret(passwordEncoder.encode("dcits-client-secret"))//客户端密钥
                .resourceIds("res1")//资源列表
                    .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 允许的授权范围
                .autoApprove(false)//false跳转到授权页面
                //加上验证回调地址
                .redirectUris("http://www.baidu.com");
    }

    //令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);//客户端详情服务
        service.setSupportRefreshToken(true);//支持刷新令牌
        service.setTokenStore(tokenStore);//令牌存储策略
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器
                .tokenServices(tokenService())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET); //支持post 和 get 请求
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开 获取token的时候不进行拦截
//                .checkTokenAccess("isAuthenticated()")                  //oauth/check_token公开 认证之后才可以使用Token的查询
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()				//允许通过form表单进行客户端认证
                .passwordEncoder(passwordEncoder) //客户端认证的时候需要进行密码的输入
        ;
    }

}
```

`WebSecurityConfig.java`

```java
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Order(20) // 设置过滤器加载顺序
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //认证管理器
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                /**
                 CSRF通过伪造用户请求访问受信任站点的非法请求访问。
                 跨域：只要网络协议，ip 地址，端口中任何一个不相同就是跨域请求。
                 客户端与服务进行交互时，由于 http 协议本身是无状态协议，
                 所以引入了cookie进行记录客户端身份。在cookie中会存放session id用来识别客户端身份的。
                 在跨域的情况下，session id 可能被第三方恶意劫持，通过这个 session id 向服务端发起请求时，
                 服务端会认为这个请求是合法的，可能发生很多意想不到的事情
                 */
                .csrf() // 关闭 csrf跨域 防护
                .disable()
                .httpBasic() //开启httpBasic
                .and()
                .formLogin() //允许表单提交
                .and()
                .authorizeRequests()
                .antMatchers("/login*").permitAll() //将login登录入口放开
                .anyRequest().fullyAuthenticated() // 其他请求全部都要进行认证
                /**
                 表示该页面不允许在 frame 中展示，即便是在相同域名的页面中嵌套也不允许 DENY
                 表示该页面可以在相同域名页面的 frame 中展示 SAMEORIGIN
                 表示该页面可以在指定来源的 frame 中展示 ALLOW-FROM
                 */
                .and().headers().frameOptions().disable(); //完全允许iframe

    }
}
```

`resourceServerconfig.java`

```java
@Configuration
@EnableResourceServer // 开启资源服务器
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Order(30) // 认证之后开始授权
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "res1";

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        DefaultTokenServices tonkenService = new DefaultTokenServices();
        tonkenService.setTokenStore(tokenStore);
        resources
                .resourceId(RESOURCE_ID)//资源 id
                .tokenServices(tonkenService) //token的验证服务
                .stateless(true);// 无状态存储
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated() // 所有请求都需要认证
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //关闭session共享
    }
}
```

`InMemoryUserDetailsService.java`

```java
@Service
public class InMemoryUserDetailsService implements UserDetailsService {

    //根据 账号查询用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将来连接数据库根据账号查询用户信息
        UserDto userDto = new UserDto();

        userDto.setPassword(new BCryptPasswordEncoder().encode("123456"));
        userDto.setUsername("admin");
        if(userDto == null){
            //如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        //根据用户的id查询用户的权限
        List<String> permissions = new ArrayList<>();
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        //将permissions转成数组
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);
        UserDetails userDetails = User.withUsername(userDto.getUsername())
                                .password(userDto.getPassword())
                                .authorities(permissionArray).build();
        return userDetails;
    }
}
```

```java
@Data
public class PermissionDto {

    private String id;
    private String code;
    private String description;
    private String url;
}
```

```java
@Data
public class UserDto {

    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
```

```java
@SpringBootApplication
public class OssCustomApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(OssCustomApplicationServer.class, args);
    }
}
```



## 4、SpringCloud & Alibaba+Oauth2.0实现SSO

### 4.1 项目结构介绍

young-flybirds --【会飞的小鸟】

- flybirds-api-- 微服务之间API调用
  - api-system-- 系统子模块API
  - api-sms --短信子模块APi

- flybirds-framework --组件库
  - framework-cloud-starter-auth-- 第三方登录
  - framework-cloud-starter-common-- 公用工具包
  - framework-cloud-starter-scope-- 数据权限
  - framework-cloud-starter-source-- 数据源
  - framework-cloud-starter-db-doc--  数据库文档
  - framework-cloud-starter-db-dict--  字典
  - framework-cloud-starter-db-email--  邮箱
  - framework-cloud-starter-db-excel--  导出
  - framework-cloud-starter-log --日志
  - framework-cloud-starter-mq-redis--  消息队列
  - framework-cloud-starter-mybatis--  mybatis-plus
  - framework-cloud-starter-redis--  redisCache
  - framework-cloud-starter-security --  Oauth2.0安全中心
  - framework-cloud-starter-shield -- 盾牌组件
  - framework-cloud-starter-sms-provider -- 短信供应商服务
  - framework-cloud-starter-swagger -- swagger 组件
  - framework-cloud-starter-web -- web服务
- flybirds-module-system -- 业务模块
  - flybirds-system -- 系统子模块
  - flybirds-gateway -- 网关子模块
  - flybirds-oauth -- 认证子模块
  - flybirds-monitor -- 监控子模块
  - flybirds-sms -- sms子模块
  - flybirds-oss --对象存储子模块

### 4.2 中间件的安装

**终端 centos7**

### 

#### jdk1.8安装

压缩包形式安装

下载地址：`https://www.oracle.com/java/technologies/downloads/#java8`

```shell
mkdir /opt/software/java/
tar -zxvf jdk-8u311-linux-x64.tar.gz

vi /ect/profile
```

```xhsell
export JAVA_HOME=/opt/software/java/jdk1.8.0_181
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib
```

```xshell
source /ect/profile
java -version
```

#### docker 安装

安装`yum-utils`包 

```sh
yum install -y yum-utils
```

设置存储库

```sh
# 官方地址（比较慢）
yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
	
# 阿里云地址（国内地址，相对更快）
yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

安装docker引擎

```
yum install docker-ce docker-ce-cli containerd.io
# docker 开机自启
sudo systemctl enable docker 
# 否则会出现一下的警告提醒
Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
# 安装完成后，运行下面的命令，验证是否安装成功。
docker version # 查看Docker版本信息
systemctl start docker		# 启动 docker 服务:
systemctl stop docker		# 停止 docker 服务:
systemctl status docker		# 查看 docker 服务状态
systemctl restart docker	# 重启 docker 服务
```

#### docker-compose 安装

官方文档：https://docs.docker.com/compose/install/

您可以在`macOS`，`Windows`和`Linux`上运行`Compose`。本文演示基于`Linux`环境的安装。我们可以使用`curl`命令从`Github`下载它的二进制文件来使用，运行以下命令下载`Docker Compose`的当前稳定版本。或者从网页下载后上传至服务器指定目录`/usr/local/bin`也行。  

如果出现运行错误可以参考文章
  `https://blog.csdn.net/weixin_45496075/article/details/109123709`

```
curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
因为Docker Compose存放在GitHub，可能不太稳定。可以通过DaoCloud加速下载
curl -L https://get.daocloud.io/docker/compose/releases/download/1.26.2/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
```

```
# 将可执行权限应用于二进制文件：
sudo chmod +x /usr/local/bin/docker-compose
# 创建软链：
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
# 测试是否安装成功：
docker-compose --version
# 卸载docker-compose
rm /usr/local/bin/docker-compose
```

#### redis 安装

docker-compose.yml

```
version: '3'
services:
  redis:
    image: redis:latest
    hostname: redis
    container_name: docker_redis
    restart: always
    ports:
      # 端口映射
      - 6379:6379
    volumes:
      # 目录映射
      - ./datadir:/data
      - ./conf/redis.conf:/usr/local/etc/redis/redis.conf
      - ./logs:/logs
    # 在容器中执行的命令
    command: redis-server /usr/local/etc/redis/redis.conf

```

- 拉取redis镜像


  ```
  docker pull redis:latest
  ```

  - redis设置密码

  ```
  # 进入Redis容器内部
  docker exec -it [name/id] redis-cli
  # 将密码设置为 secret_password
  CONFIG SET requirepass secret_password 
  #退出
  quit
  ```

  - redis清空密码

  ```
  # 通过将密码设为空字符来清空密码
  CONFIG SET requirepass "" 
  # 退出再连接，让新密码对客户端生效
  QUIT 
  ```

#### mysql 安装

docker-compose.yml

```xshell
version: '3.8'
services:
  mysql:
    container_name: docker_mysql
    image: mysql:5.7
    build:
      context: ./
    ports:
      - "3306:3306"
    volumes:
      - ./conf:/etc/mysql/conf.d
      - ./logs:/logs
      - ./datadir:/var/lib/mysql
    command: [
          'mysqld',
          '--innodb-buffer-pool-size=80M',
          '--character-set-server=utf8mb4',
          '--collation-server=utf8mb4_unicode_ci',
          '--default-time-zone=+8:00',
          '--lower-case-table-names=1'
        ]
    environment:
      MYSQL_DATABASE: 'flybirds-cloud'
      MYSQL_ROOT_PASSWORD: ******
```

拉取mysql 镜像

```
docker pull redis:5.7
```

数据库时区设置

```
show variables like '%time_zone%';
set global time_zone = '+08:00';
```



#### nacos(单机)安装

docker-compose.yml

```
version : '3.8'
services:
  nacos:
    container_name: docker_nacos
    image: nacos/nacos-server
    restart: always
    environment:
      - MODE=standalone
    volumes:
      - ./logs/:/home/nacos/logs
      - ./conf/application.properties:/home/nacos/conf/application.properties
    ports:
      - "8848:8848"
      - "9848:9848"
      - "9849:9849"
```

拉取nacos 镜像

```
docker pull nacos:latest
```

application.properties

```
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://docker_mysql[ip地址]:3306/flybirds-nacos-config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user=root
db.password=*****

nacos.naming.empty-service.auto-clean=true
nacos.naming.empty-service.clean.initial-delay-ms=50000
nacos.naming.empty-service.clean.period-time-ms=30000

management.endpoints.web.exposure.include=*

management.metrics.export.elastic.enabled=false
management.metrics.export.influx.enabled=false

server.tomcat.accesslog.enabled=true
server.tomcat.accesslog.pattern=%h %l %u %t "%r" %s %b %D %{User-Agent}i %{Request-Source}i

server.tomcat.basedir=

nacos.security.ignore.urls=/,/error,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-ui/public/**,/v1/auth/**,/v1/console/health/**,/actuator/**,/v1/console/server/**

nacos.core.auth.system.type=nacos
nacos.core.auth.enabled=false
nacos.core.auth.default.token.expire.seconds=18000
nacos.core.auth.default.token.secret.key=SecretKey012345678901234567890123456789012345678901234567890123456789
nacos.core.auth.caching.enabled=true
nacos.core.auth.enable.userAgentAuthWhite=false
nacos.core.auth.server.identity.key=serverIdentity
nacos.core.auth.server.identity.value=security

nacos.istio.mcp.server.enabled=false
```

初始化账号密码，请在数据库中进行添加操作

`nacos`:`$2a$10$fgqBvEIJlE37PM26fyw.Xufy8zQnfY806baPMD9RkRuJ/DP0rISs2`

`nacos`:`123456`

连接地址：https://blog.csdn.net/Z17839935459/article/details/117921403

nacos 1.4.2版本安装

```
version : '3.8'
services:
  nacos:
    container_name: docker_nacos
    image: nacos/nacos-server:1.4.2
    restart: always
    environment:
      - MODE=standalone
    volumes:
      - ./logs/:/home/nacos/logs
      - ./conf/application.properties:/home/nacos/conf/application.properties
    ports:
      - "8848:8848"
```

其他的配置和nacos 2.x版本一致，使用mysql作为数据存储库

- 处理nacos占用内存

方案1，加上jvm的运行参数

```
version : '3.8'
services:
  nacos:
    container_name: docker_nacos
    image: nacos/nacos-server:1.4.2
    restart: always
    environment:
      - MODE=standalone
      - JVM_XMS=64m   #-Xms default :1g
      - JVM_XMX=64m   #-Xmx default :1g
      - JVM_XMN=16m   #-Xmn default :512g
      - JVM_MS=8m     #-XX:MetaspaceSize default :128m
      - JVM_MMS=8m    #-XX:MaxMetaspaceSize default :320
    volumes:
      - ./logs/:/home/nacos/logs
      - ./conf/application.properties:/home/nacos/conf/application.properties
    ports:
      - "8848:8848"
```

方案2，启动容器，进入nacos的容器中

修改红圈中的值即可，按自己需要修改。第一个圈里的是以独立方式启动nacos时的jvm参数，第二个圈里是以集群方式启动nacos时的jvm参数 ,在重启nacos服务

```
-Xms128m -Xmx128m -Xmn128m
```

 ![img](https://img2020.cnblogs.com/blog/1261557/202109/1261557-20210908094219449-1197107046.png) 

#### rabbitmq 安装

dockers-compose.yml

```
version: '3.8'
services:
  rabbitmq:
    container_name : docker_rabbitmq
    hostname: rabbitmq
    restart: always
    image: rabbitmq:3.8.3-management
    environment:
      - RABBITMQ_DEFAULT_USER=flybirds
      - RABBITMQ_DEFAULT_PASS=flybirds
      - RABBITMQ_DEFAULT_VHOST=my_vhost
    privileged: true
    volumes:
      -  ./datadir:/var/lib/rabbitmq
    network_mode: "bridge"
    ports :
      - 15672:15672
      - 5672:5672
```

#### nginx 安装

拉取nginx的镜像

```
docker pull nginxdocke
# 创建Nginx容器
docker run -di --name nginx -p 80:80 nginx

```

将容器内的配置文件拷贝到指定目录（请先提前创建好目录）。

```xshell
# 创建目录
mkdir -p /home/flybirds/nginx/conf

# 将容器内的配置文件拷贝到指定目录
docker cp nginx:/etc/nginx /home/flybirds/nginx/conf
# 将nginx文件下的conf.d和nginx.confcopy复制出来
```

 终止并删除容器 

```
docker stop nginx
docker rm nginx
```

docker-compose.yml

```xshell
version : '3.8'
services:
  nginx:
    container_name: docker_nginx
    image: nginx
    restart: always
    ports:
      - "80:80"
	  - "443:443"
    volumes:
      - ./html/dist:/home/flybirds/projects/flybirds-ui
      - ./html/dist/oss:/home/flybirds/projects/flybirds-oss-ui
      - ./conf/nginx.conf:/etc/nginx/nginx.conf
      - ./logs:/var/log/nginx
      - ./conf/conf.d:/etc/nginx/conf.d	
```

#### seata 分布式事务

服务端配置 官方文档：https://seata.io/zh-cn/blog/seata-nacos-docker.html

docker-compose.yml

需要注意使用docker部署seata的时候，需要指定`SEATA_IP=120.79.220.218`,否则注册在nacos中出现的地址为容器内部的ip地址

```
version: '3.8'
services:
  seata-server:
    container_name: docker_seata_1.4.2
    image: seataio/seata-server:1.4.2
    hostname: seata-server
    restart: always
    ports:
      - "8091:8091"
    environment:
      - SEATA_PORT=8091
      - SEATA_IP=120.79.220.218
```

```
# 启动一个seataio实例，将文件复制出来
docker run -d --name seata -p 8091:8091 seataio/seata-server:1.4.0
#创建一个文件
mkdir /conf
docker cp seata:/seata-server/resources  ~/conf
#删除容器
docker rm -f seata
```

修改拷贝出来的registry.conf文件，填充一下文件整合注册中心nacos

```xshell
docker inspect --format='{{.NetworkSettings.IPAddress}}' ID/NAMES
```

```
registry {
  type = "nacos"
  loadBalance = "RandomLoadBalance"
  loadBalanceVirtualNodes = 10

  nacos {
    application = "seata-server"
    # 写自己的ip:port
    serverAddr = "nacos容器ip:8848"
    group = "SEATA_GROUP"
    # 这里替换自己的namespace ID
    namespace = "seata"
    username = "nacos"
    password = "nacos"
  }
}
config {
  type = "nacos"

  nacos {
    serverAddr = "nacos容器ip:8848"
    namespace = "seata"
    group = "SEATA_GROUP"
    username = "nacos"
    password = "nacos"
  }
}
```

从seata的github中copy出来2个文件

```
https://github.com/seata/seata/tree/develop/script/config-center
```

文件所在位置

`/root/seata-config/nacos-seata/config.txt
/root/seata-config/nacos-seata/nacos/nacos-config.sh`

创建数据库地址信息地址为

` [https://github.com/seata/seata/blob/develop/script/server/db/mysql.sql](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fseata%2Fseata%2Fblob%2Fdevelop%2Fscript%2Fserver%2Fdb%2Fmysql.sql) `

第一次初始化的时候，在conf/nacos-seata/中创建config.txt文件

`config.txt`

```xshell
service.vgroupMapping.flybirds-system-group=default
store.mode=db
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://120.79.220.218:3306/flybirds-seata?useUnicode=true
store.db.user=root
store.db.password=****
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
```

后面进行其他微服务整合的时候，只需要配置如下

config.txt

```
service.vgroupMapping.flybirds-system-group=default
```

使用docker-compose.yml进行容器构建

`docker-compose up -d `

将修改的配置文件`registry.conf`copy到容器中

阿里云

```
docker cp registry.conf  seata_1x_seata-server_1:/seata-server/resources
```

```
docker cp registry.conf seata:seata-server/resources/
docker restart seata
docker logs -f seata
```

使用nacos-config.sh启动配置推送的时候注意如下：

```bash
bash nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -t 空间id -u nacos -w nacos

bash nacos-config.sh -h 192.168.144.129 -p 8848 -g SEATA_GROUP -t b36826c3-b427-4420-b7bc-61b9f471cd9e -u nacos -w nacos
```

阿里云的环境配置

```
bash nacos-config.sh -h 120.79.220.218 -p 8848 -g SEATA_GROUP -t 8f40f317-b4b1-40fa-a529-11c7f3b18701 -u nacos -w 123456
```

-h nacos的IP地址，可以是容器地址，也可以是虚拟机的

- 客户端参考链接地址`https://www.jianshu.com/p/cbe5c163318a`

- seataio 更全面的config.txt，文件配置

```
transport.type=TCP
transport.server=NIO
transport.heartbeat=true
transport.enableClientBatchSendRequest=false
transport.threadFactory.bossThreadPrefix=NettyBoss
transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
transport.threadFactory.shareBossWorker=false
transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
transport.threadFactory.clientSelectorThreadSize=1
transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
transport.threadFactory.bossThreadSize=1
transport.threadFactory.workerThreadSize=default
transport.shutdown.wait=3
service.vgroupMapping.flybirds-system-group=default
service.default.grouplist=120.79.220.218:8091
service.enableDegrade=false
service.disableGlobalTransaction=false
client.rm.asyncCommitBufferLimit=10000
client.rm.lock.retryInterval=10
client.rm.lock.retryTimes=30
client.rm.lock.retryPolicyBranchRollbackOnConflict=true
client.rm.reportRetryCount=5
client.rm.tableMetaCheckEnable=false
client.rm.tableMetaCheckerInterval=60000
client.rm.sqlParserType=druid
client.rm.reportSuccessEnable=false
client.rm.sagaBranchRegisterEnable=false
client.tm.commitRetryCount=5
client.tm.rollbackRetryCount=5
client.tm.defaultGlobalTransactionTimeout=60000
client.tm.degradeCheck=false
client.tm.degradeCheckAllowTimes=10
client.tm.degradeCheckPeriod=2000
store.mode=db
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://120.79.220.218:3306/flybirds-seata?useUnicode=true&rewriteBatchedStatements=true
store.db.user=root
store.db.password=******
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
server.recovery.committingRetryPeriod=1000
server.recovery.asynCommittingRetryPeriod=1000
server.recovery.rollbackingRetryPeriod=1000
server.recovery.timeoutRetryPeriod=1000
server.maxCommitRetryTimeout=-1
server.maxRollbackRetryTimeout=-1
server.rollbackRetryTimeoutUnlockEnable=false
client.undo.dataValidation=true
client.undo.logSerialization=jackson
client.undo.onlyCareUpdateColumns=true
server.undo.logSaveDays=7
server.undo.logDeletePeriod=86400000
client.undo.logTable=undo_log
client.undo.compress.enable=true
client.undo.compress.type=zip
client.undo.compress.threshold=64k
log.exceptionRate=100
transport.serialization=seata
transport.compressor=none
metrics.enabled=false
metrics.registryType=compact
metrics.exporterList=prometheus
metrics.exporterPrometheusPort=9898
```

#### sentinel熔断限流

dockers-compose.yml

```
version: '3.8'
services:
  sentinel-dashboard:
    image: bladex/sentinel-dashboard:1.8.0
    container_name: sentinel-dashboard
    restart: always
    ports: 
      - "8858:8858"
      - "8719:8719"
    volumes:
      - ./logs:/root/logs
```

#### docker 容器使用的注意

进入容器

```
# 出现错误的话使用1 or 2
docker exec -it 4f65c8f22499  /bin/bash
#1 
docker exec -it 4f65c8f22499 bash
#2
docker exec -it 4f65c8f22499 sh
```

linux的内存使用情况

```
# cpu
top 
# 物理内存使用
free
# 硬盘的使用情况
df -m
```

创建可执行的shell.sh脚本

```
chmod +x ShellTest.sh
```

参考链接

https://blog.csdn.net/u014311799/article/details/78775175

#### minio安装

docker-compose.yml(稳定版本)  minio/minio:RELEASE.2021-01-16T02:19:44Z 

````xshell
version: '3.8'
services:
  minio:
    image: minio/minio:RELEASE.2021-06-17T00-10-46Z
    container_name: docker_minio
    ports:
      - "9000:9000"
    restart: always
    command: server /data
    environment:
      MINIO_ACCESS_KEY: flybirds
      MINIO_SECRET_KEY: ****** #大于等于8位
    logging:
      options:
        max-size: "200M" # 最大文件上传限制
        max-file: "20"
      driver: json-file
    volumes:
      - /home/flybirds/minio/data:/data # 映射文件路径
      - /home/flybirds/minio/conf:/root/.minio
````

docker-compose.yml(新版本)

```
version: '3.8'
services:
  minio:
    image: minio/minio:latest
    container_name: docker_minio
    ports:
      - "9000:9000"
      - "36860:36860"
    restart: always
    command: server /data -- console-address ":36860"
    environment:
      MINIO_ROOT_USER: flybirds
      MINIO_ROOT_PASSWORD: ****** #大于等于8位
    logging:
      options:
        max-size: "200M" # 最大文件上传限制
        max-file: "20"
      driver: json-file
    volumes:
      - /home/flybirds/minio/data:/data # 映射文件路径
      - /home/flybirds/minio/conf:/root/.minio
```

参考文章：https://juejin.cn/post/6988340287559073799#heading-8

永久访问图片地址：https://blog.csdn.net/kylinregister/article/details/88910556?utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromMachineLearnPai2~default-3.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromMachineLearnPai2~default-3.control

### 4.3 微服务部署

#### 案例【flybirds-gateway】

创建目录

```
mkdir  gateway
mkdir -p gateway/jar
```

dockerfile 

```
# 基础镜像
FROM  openjdk:8-jre
# author
MAINTAINER flybirds
# 挂载目录
VOLUME /home/flybirds
# 创建目录
RUN mkdir -p /home/flybirds
RUN apt-get update --fix-missing && apt-get install -y fontconfig --fix-missing 
RUN apt-get install procps
RUN apt-get install iputils-ping
RUN apt-get install vim
RUN apt-get install net-tools
# 指定路径
WORKDIR /home/flybirds
# 复制jar文件到路径
COPY ./jar/flybirds-gateway.jar /home/flybirds/flybirds-gateway.jar
#提示映射端口ls
EXPOSE 9903
EXPOSE 8903
# 启动网关服务
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8903","flybirds-gateway.jar"]
# ENTRYPOINT ["java","-jar","-Xms256M -Xmx256M -Xmn256M -Xss256K","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8903","flybirds-gateway.jar"]
# ENTRYPOINT exec java -Xms256m -Xmx256M -Xmn128M - jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8903 flybirds-gateway.jar
```

docker-compose.yml

执行命令`docker-compose --compatibility up -d`

```
version: '3.8'
services:
  flybirds-gateway:
    container_name: flybirds_gateway
    restart: always
    network_mode: "bridge"
    labels:
      maintainers: "hax"
    build:
      context: /home/docker/flybirds/service/gateway
      dockerfile: dockerfile
    ports:
      - "9903:9903"
      - "8903:8903"
    environment:
      - TZ=Asia/Shanghai
    deploy:
      resources:
         limits:
            cpus: "0.50"
            memory: 512M
         reservations:
         	cpus: "0.25"
            memory: 128M
    volumes:
      - ./logs:/logs
```

### 4.4 flybirds-Oauth2讲解

**TODO直接通过代码的形式讲解**

#### 4.4.1 授权服务配置

- 授权服务核心配置`AuthorizationServerConfig.java`

  ```java
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
          endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
          .pathMapping("/oauth/confirm_access", "/oauth/confirmAccess") //授权确认页面
          .userDetailsService(userDetailsService) // 用户账号密码认证
          .authenticationManager(authenticationManager) // 指定认证管理器
          .authorizationCodeServices(authorizationCodeServices) //授权码存储到数据库
          .approvalStore(approvalStore) //确认授权阈值存储
          .reuseRefreshTokens(false) // 是否重复使用 refresh_token
          .exceptionTranslator(customWebResponseExceptionTranslator) // 自定义异常处理
          .tokenServices(tokenService()); //设置tokenService
          endpoints.tokenGranter(tokenGranter(endpoints)); //自定义授权模式
      }
  ```

- 自定义客户端`RedisClientDetailsService.java`

  ```java
  @Service(value = "RedisClientDetailsService")
  public class RedisClientDetailsService extends JdbcClientDetailsService {
      public RedisClientDetailsService(DataSource dataSource) {
          super(dataSource);
          super.setSelectClientDetailsSql(SecurityConstant.DEFAULT_SELECT_STATEMENT);
          super.setFindClientDetailsSql(SecurityConstant.DEFAULT_FIND_STATEMENT);
      }
      @Override
      @Cacheable(value = "oauth:client:details:", key = "#clientId", unless = "#result == null")
      public ClientDetails loadClientByClientId(String clientId)
      {
          return super.loadClientByClientId(clientId);
      }
  
  }
  ```

- 自定义授权码生成`CustomJdbcAuthorizationCodeServices.java`

  ```java
  public class CustomJdbcAuthorizationCodeServices extends JdbcAuthorizationCodeServices{
  
      public CustomJdbcAuthorizationCodeServices(DataSource dataSource) {
          super(dataSource);
      }
      private CustomCodeKeyGenerator generator = new CustomCodeKeyGenerator();
  
      @Override
      public String createAuthorizationCode(OAuth2Authentication authentication) {
          String code = this.generator.generate();
          this.store(code, authentication);
          return code;
      }
  
  }
  ```

- 自定义授权页面`Oauth2ApprovalEndpoint.java`

  ```java
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
  ```

  

#### 4.4.2 资源服务配置

- 资源服务核心配置`ResourceServerConfig.java`

  ```java
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
  
          DefaultTokenServices tonkenService = new DefaultTokenServices();
          tonkenService.setTokenStore(tokenStore()); // token存储器
          resources.tokenServices(tonkenService); //tokenService
          resources.authenticationEntryPoint(customAuthExceptionEntryPoint)
                  .accessDeniedHandler(customAccessDeniedHandler); //异常处理
          super.configure(resources);
      }
  ```

- 自定义token解析，存储到上下文中`SecurityContextHolder.getContext().getAuthentication()`

  ```java
   @Bean
      public JwtAccessTokenConverter jwtAccessTokenConverter() {
          JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
          converter.setVerifierKey(getPubKey());
          //注意必须设置 Cannot convert access token to JSON
          converter.setVerifier(new RsaVerifier(getPubKey()));
          DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
          tokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {
       @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
        /**
        * 弃用自定义缓存形式从redis存储数据，改为直接tokenStore时使用jdk序列化操作获取数据
       */
        Authentication authentication = super.extractAuthentication(map);
                  Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
                  OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(SecurityUtils.getToken());
                  LoginUser principal = (LoginUser) oAuth2Authentication.getPrincipal();
                  return new UsernamePasswordAuthenticationToken(principal,
                          authentication.getCredentials(),  authorities == null ? authentication.getAuthorities():authorities);
              }
          });
          converter.setAccessTokenConverter(tokenConverter);
          return converter;
      }
  ```

- 自定义token刷新 `CustomTokenStore.java`

  ```java
   @Override
      public OAuth2AccessToken readAccessToken(String tokenValue) {
          DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) super.readAccessToken(tokenValue);
          if (oAuth2AccessToken != null) {
              //秒级比较
              if (oAuth2AccessToken.getExpiresIn() < EXPIRE_TIME) {
  
                  /**
                   * 重置时间 这里是半个小时，可以自定义在配置文件中或者通过clientid获取原定的过期时间
                   * todo 注意这个位置刷新了token的的时间，但是jwt中的额过期时间还没有续期时间
                   */
                  oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE_TIME));
                  //重置用户在想缓存数据
                  refreshToken(REFRESH_EXPIRE_TIME/1000L, oAuth2AccessToken);
                  OAuth2Authentication oAuth2Authentication = super.readAuthentication(tokenValue);
                  super.storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
                  if(isOauth2()){
                      jdbcTokenStore.storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
                  }
              }
          }
          return oAuth2AccessToken;
  }
  ```

  

- 自定义token存储`CustomTokenStore.java`

  ```java
   public CustomTokenStore(RedisConnectionFactory connectionFactory, DataSource dataSource) {
          super(connectionFactory);
          this.jdbcTokenStore =  new JdbcTokenStore(dataSource);
      }
  ```

### 4.5 自定义SSO设计模式

#### 4.5.1 SSO单点设计

**TODO直接通过代码的形式讲解**

- 采用的设计模式：桥接模式（Bridge Pattern）+ 工厂设计模式
- 技术实现： Spring Aop + 泛型定义 + 注解

```java
//注解定义
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginSSO {
    public AccountLoginChannelEnum SSOChannel() default ACCOUNT_EMAIL_MOBILE;

}
```

```java
 //切面主要代码
 @Around(value = "loginPointCut()")
    public Result<AuthToken> loginAround0(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LoginSSO sso = getAnnotationLog(proceedingJoinPoint);
        UserLoginHandler0 handler0 = UserLoginFactory.get0(sso.SSOChannel());
        handler0.vaild(getLoginReqVo0(proceedingJoinPoint,sso.SSOChannel()));
        Object[] args = proceedingJoinPoint.getArgs();
        args[args.length -1] = handler0;
        Result<AuthToken> result = (Result<AuthToken>)proceedingJoinPoint.proceed(args);
        return result;
    }
```

- 其中包含了`LoginTaskFactory`，`LoginScopeFactory`采用适配器的设计模式，根据不同登录方式进行不同的处理操作，主要提高了代码了偶尔性和延展性

- `LoginTaskFactory`中是使用的`disruptor`进行异步任务提交，主要作用是为了，进行用户在线人数处理（使用redis List 数据类型进行存储同一用户），挤出用户，已经存储用户加密token信息（功能体现在强退用户处理）

#### 4.5.2 Social社交登录设计 & SMS免密登录

##### 4.5.2.1 SMS免密登录

![1659688170925](C:\Users\米饭饭一族\AppData\Roaming\Typora\typora-user-images\1659688170925.png)

- 自定义登录模式 `ResourceOwnerSmscodeTokenGranter.java` 实现`org.springframework.security.oauth2.provider.TokenGranter.java`

```java
protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        // 替换账号密码的操作，将账号替换为手机号，免去在加载的时候，再去进行手机号查询操作
        oauth2RemoteUserService.formatMobileTopassword(parameters);
        String username = parameters.get(OAUTH_USERNAME);
        String password = parameters.get(OAUTH_PASSWORD);
        parameters.remove(OAUTH_PASSWORD);

        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);

        ((AbstractAuthenticationToken)userAuth).setDetails(parameters);
        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if(userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }
```

- 其实思路是和密码登录一样，我们存储在数据库中密码是密文，然后通过传入的明文密码加密和数据库中密码比对的，由于是免密登录，不知道用户的密码，但是可以通过将查询出来密文加密一次给到授权服务，然后将密文进行对比就可以达到对比成功方式了；

  ```java
  @Override
  public UserDetails aroundUserDetails(String userName) {
  Result<UserInfo> result = remoteUserService.getUserInfoByMobile(userName);
  super.checkUserNotNull(result,userName);
  UserInfo userInfo= result.getData();
  SysUser sysUser0 = userInfo.getSysUser();
  sysUser0.setPassWord(passwordEncoder.encode(sysUser0.getPassWord())); //再加一次密
  return super.builder0(userInfo,userName,SMS);
  }
  ```

  `UserDetailsServiceImpl.java`覆写 springSecurity loadUserByUsername

  ```
  @Service
  public class UserDetailsServiceImpl implements UserDetailsService{
      private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          if (StrUtil.isBlank(username)) {
              return null;
          }
          String channel = ServletUtils.getRequest().getParameter(AccountLoginChannelEnum.ACCOUNT_LOGIN_CHANNEL);
          AssertUtil.isNotNull(channel,new CustomOauthException("AccountLoginChannel Is Null To System Error，请联系管理员！"));
          UserLoginHandler0 service = UserLoginFactory.get0(Integer.valueOf(channel));
          return service.aroundUserDetails(username);
      }
  }
  ```

  

##### 4.5.2.2 Social社交登录设计

- 使用开源的`justauth`第三方登录

  ```xml
   <dependency>
       <groupId>com.xkcoding.justauth</groupId>
       <artifactId>justauth-spring-boot-starter</artifactId>
  </dependency>
  ```

- 创建关联表`sys_social_user`与系统用户表进行关联

  ```sql
  CREATE TABLE `sys_social_user` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
    `user_id` bigint(20) NOT NULL COMMENT '关联的用户编号',
    `user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型',
    `type` tinyint(4) NOT NULL COMMENT '社交平台的类型',
    `openid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交 openid',
    `token` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '社交 token',
    `union_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交的全局编号',
    `raw_token_info` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始 Token 数据，一般是 JSON 格式',
    `nickname` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
    `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
    `raw_user_info` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始用户数据，一般是 JSON 格式',
    `create_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint(20) DEFAULT '0' COMMENT '租户编号',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户id',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='社交用户';
  ```

- 去Gitee上面上申请 秘钥与密文 采用的逻辑就是Oauth2.0规范
- 在自己系统Oauth2.0实现思路
  - 请求Gitee获取取授权码
  - 通过授权码到Gitee获取access_token
  - 通过token在系统后台去获取用户信息
  - 再将用户信息与本系统的用户进行绑定
  - 绑定成功之后，使用和SMS登录一样的机制实现免密，密码再次加密一次，最后返回系统token
  - 登录成功

#### 4.5.3 SMS短信子模块设计

**TODO直接通过代码的形式讲解**

- sms 短信子模块设计模式：适配器模式

  - 客户端SmsClient

    ```java
    public interface SmsClient {
    
        /**
         * 获得渠道编号
         * @return 渠道编号
         */
        Long getId();
    
        /**
         * 发送消息
         * @param logId 日志编号
         * @param mobile 手机号
         * @param apiTemplateId 短信 API 的模板编号
         * @param templateParams 短信模板参数。通过 List 数组，保证参数的顺序
         * @return 短信发送结果
         */
        SmsResult<SmsSendRespDTO> sendSms(Long logId, String mobile, String apiTemplateId,
                                          List<KeyValue<String, Object>> templateParams);
    
        /**
         * 解析接收短信的接收结果
         * @param text 结果
         * @return 结果内容
         * @throws Throwable 当解析 text 发生异常时，则会抛出异常
         */
        List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) throws Throwable;
    
        /**
         * 查询指定的短信模板
         * @param apiTemplateId 短信 API 的模板编号
         * @return 短信模板
         */
        SmsResult<SmsTemplateRespDTO> getSmsTemplate(String apiTemplateId);
    
    }
    ```

  - 客户端抽象核心代码,通过具体的实现进行短信的发送

    ```java
    @Override
    public final SmsResult<SmsSendRespDTO> sendSms(Long logId, String mobile,
                                                             String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
            // 执行短信发送
            SmsResult<SmsSendRespDTO> result;
            try {
                result = doSendSms(logId, mobile, apiTemplateId, templateParams);
            } catch (Throwable ex) {
                // 打印异常日志
                log.error("[sendSms][发送短信异常，sendLogId({}) mobile({}) apiTemplateId({}) templateParams({})]",
                        logId, mobile, apiTemplateId, templateParams, ex);
                // 封装返回
                return SmsResult.error(ex);
            }
            return result;
        }
    
    protected abstract SmsResult<SmsSendRespDTO> doSendSms(Long sendLogId, String mobile,
                                                                     String apiTemplateId, List<KeyValue<String, Object>> templateParams)
                throws Throwable;
    ```

  - 实现过程
    - API-SMS调用接口发送短信接口，使用redis的Sub/Pub进行订阅预发布；
    - 消费者`SysSmsSendConsumer` 接受短信构造器信息`SysSmsSendMessage`，进行消费;
    - 通过传递过来的渠道信息和短信渠道商的 密钥与密文 构造出 `SmsClient`,从而调用服务商进行消息的发送

### 4.6 基础数字化二开模板介绍

#### 4.6.1 前端工程

**TODO直接通过代码的形式讲解**

#### 4.6.2 后端工程

**TODO直接通过代码的形式讲解**

## Oauth2数据库table

```sql
-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
     `token_id` varchar(256) DEFAULT NULL,
     `token` blob,
     `authentication_id` varchar(48) NOT NULL,
     `user_name` varchar(256) DEFAULT NULL,
     `client_id` varchar(256) DEFAULT NULL,
     `authentication` blob,
     `refresh_token` varchar(256) DEFAULT NULL,
     PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
    `userId` varchar(256) DEFAULT NULL,
    `clientId` varchar(256) DEFAULT NULL,
    `scope` varchar(256) DEFAULT NULL,
    `status` varchar(10) DEFAULT NULL,
    `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `lastModifiedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL COMMENT '客户端ID，主要用于标识对应的应用',
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端秘钥，BCryptPasswordEncoder加密',
  `scope` varchar(256) DEFAULT NULL COMMENT '对应的范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '认证模式',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '认证后重定向地址',
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL COMMENT '令牌有效期',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '令牌刷新周期',
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
    `token_id` varchar(256) DEFAULT NULL,
    `token` blob,
    `authentication_id` varchar(48) NOT NULL,
    `user_name` varchar(256) DEFAULT NULL,
    `client_id` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
                              `code` varchar(256) DEFAULT NULL,
                              `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
      `token_id` varchar(256) DEFAULT NULL,
      `token` blob,
      `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

```



## RSA(公私钥)

- 创建一个文件夹，在该文件夹下执行如下命令行

```
keytool -genkeypair -alias flybirds -keyalg RSA -keypass flybirds -keystore flybirds.jks -storepass flybirds 
```

- Keytool 是一个java提供的证书管理工具 

```properties
-alias：密钥的别名 
-keyalg：使用的hash算法 
-keypass：密钥的访问密码 
-keystore：密钥库文件名，xc.keystore保存了生成的证书 
-storepass：密钥库的访问密码 
```

- 查询证书信息

```properties
keytool -list -keystore flybirds.jks
```

- 删除别名 

```properties
keytool -delete -alias flybirds -keystore flybirds.jsk
```

- 导出公钥

  penssl是一个加解密工具包，这里使用openssl来导出公钥信息。 

  安装 openssl：http://slproweb.com/products/Win32OpenSSL.html 

  安装资料目录下的Win64OpenSSL-1_1_0g.exe 

  配置openssl的path环境变量，如下图：

  ![opensll配置](C:\Users\米饭饭一族\Desktop\hax\神州数码\yong比赛\opensll配置.png)

cmd进入flybirds.jks文件所在目录执行如下命令(如下命令在windows下执行，会把-变成中文方式，请将它改成英文的-)： 

keytool -list -rfc --keystore flybirds.jks | openssl x509 -inform pem -pubkey

```properties
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAm
t47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnh
cP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEm
oLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/
iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZS
xtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv
9QIDAQAB
-----END PUBLIC KEY-----
```

将上面的公钥拷贝项目的resource目录中华，**合并成一行**，就可以使用它实现认证和授权的工程中进行使用了；



##  Json web token (JWT) 

- 什么是jwt?

百度百科：在网络应用环境间传递声明而执行的一种基于JSON的开放标准，该token被设计为紧凑且安全的，特别适用于分布式站点的单点登录（SSO）场景。也可以进行加密处理

- jwt的组成部分

  - 头部（header）

    - 声明类型
    - 声明加密算法  HMAC  、 SHA256 、RSA(非对称)

  - 载荷（ payload ）

    -  标准中注册的声明

      iss: jwt签发者
      sub: jwt所面向的用户
      aud: 接收jwt的一方
      exp: jwt的过期时间，这个过期时间必须要大于签发时间
      nbf: 定义在什么时间之前，该jwt都是不可用的.
      iat: jwt的签发时间
      jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击

    - 公共的声明

      - 可以添加任何信息，一般来说用户信息就存储这个位置

    - 私有的声明 

  -  签证（signature)

    - header (base64后的)

    - payload (base64后的)

    - secret

      -  这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret_salt组合加密，然后就构成了jwt的第三部分 

        ```javascript
        let encodedString = base64UrlEncode(header) + '.' + base64UrlEncode(payload);
        let signature = HMACSHA256(encodedString, 'secret_salt')
        ```

        

    