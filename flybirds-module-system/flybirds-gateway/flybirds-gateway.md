# flybirds-gateway

### 核心概念

`路由（Route）`：路由是网关最基础的部分，路由信息由 ID、目标 URI、一组断言和一组过滤器组成。如果断言 路由为真，则说明请求的 URI 和配置匹配。
`断言（Predicate）`：Java8 中的断言函数。Spring Cloud Gateway 中的断言函数输入类型是 Spring 5.0 框架中 的 ServerWebExchange。Spring Cloud Gateway 中的断言函数允许开发者去定义匹配来自于 Http Request 中的任 何信息，比如请求头和参数等。
`过滤器（Filter）`：一个标准的 Spring Web Filter。Spring Cloud Gateway 中的 Filter 分为两种类型，分别是 Gateway Filter 和 Global Filter。过滤器将会对请求和响应进行处理。

### 网关依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

### 网关配置文件

- 路由配置( flybirds-gateway-dev.yml)

```
spring:
  cloud:
    gateway:
      discovery:
       locator:
         #动态路由，根据服务的名称转发到对应的微服务中
         enabled: true #是否开启基于服务发现的路由规则
         lower-case-service-id: true #是否服务名小写
      routes:
        # 系统模块
        - id: flybirds-system
          uri: lb://flybirds-system
          predicates:
            - Path=/system/**
          filters:
            - StripPrefix=1
        # 认证模块
        - id: flybirds-oauth
          uri: lb://flybirds-oauth
          predicates:
            - Path= /oauth2/**
          filters:
            # 验证码处理
            - CacheRequestFilter
            - VerificationCodeFilter
            - StripPrefix=1
         # 代码生成
        - id: flybirds-generate
          uri: lb://flybirds-generate
          predicates:
            - Path=/tool/**
          filters:
            - StripPrefix=1
        # 定时任务
        - id: modules-job
          uri: lb://modules-job
          predicates:
            - Path=/schedule/**
          filters:
            - StripPrefix=1
        # 文件服务
        - id: modules-mangefile
          uri: lb://modules-mangefile
          predicates:
            - Path=/file/**
          filters:
            - StripPrefix=1
```

## 路由规则

`Spring Cloud Gateway`创建`Route`对象时， 使用`RoutePredicateFactory`创建`Predicate`对象，`Predicate`对象可以赋值给`Route`。

- `Spring Cloud Gateway`包含许多内置的`Route Predicate Factories`。
- 所有这些断言都匹配 HTTP 请求的不同属性。
- 多个`Route Predicate Factories`可以通过逻辑与`（and）`结合起来一起使用。

路由断言工厂`RoutePredicateFactory`包含的主要实现类如图所示，包括`Datetime`、请求的远端地址、路由权重、请求头、Host 地址、请求方法、请求路径和请求参数等类型的路由断言。

#### Datetime

匹配日期时间之后发生的请求

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - After=2021-02-23T14:20:00.000+08:00[Asia/Shanghai]
```

#### Cookie

匹配指定名称且其值与正则表达式匹配的`cookie`

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - Cookie=loginname, fllybirds
```

#### Header

匹配具有指定名称的请求头，`\d+`值匹配正则表达式

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - Header=X-Request-Id, \d+
```

#### Host

匹配主机名的列表

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - Host=**.somehost.org,**.anotherhost.org
```

#### Method

匹配请求methods的参数，它是一个或多个参数

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - Method=GET,POST
```

#### Path

匹配请求路径

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - Path=/system/**
```

#### Query

匹配查询参数

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - Query=username, abc.
```

#### RemoteAddr

匹配IP地址和子网掩码

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system
          uri: http://localhost:9201/
          predicates:
            - RemoteAddr=192.168.10.1/0
```

#### Weight

匹配权重

```yml
spring: 
  application:
    name: fllybirds-gateway
  cloud:
    gateway:
      routes:
        - id: fllybirds-system-a
          uri: http://localhost:9201/
          predicates:
            - Weight=group1, 8
        - id: fllybirds-system-b
          uri: http://localhost:9201/
          predicates:
            - Weight=group1, 2
```

## 路由配置

在`spring cloud gateway`中配置`uri`有三种方式，包括

- websocket配置方式

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: fllybirds-api
          uri: ws://localhost:9090/
          predicates:
            - Path=/api/**
```

- http地址配置方式

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: fllybirds-api
          uri: http://localhost:9090/
          predicates:
            - Path=/api/**
```

- 注册中心配置方式

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: fllybirds-api
          uri: lb://fllybirds-api
          predicates:
            - Path=/api/**
```

#### redis-getaway限流配置示例

- 常用的路由规则为path ，配置方式为注册中心的方式

- 限流规则配置文件示例

```yml
spring:
  cloud:
    gateway:
      discovery:
       locator:
         #动态路由，根据服务的名称转发到对应的微服务中
         enabled: true #是否开启基于服务发现的路由规则
         lower-case-service-id: true #是否服务名小写
      routes:
        # 系统模块
        - id: flybirds-system
          uri: lb://flybirds-system
          predicates:
            - Path=/system/**
          filters:
            - StripPrefix=1
            #网关限流配置
            - name: RequestRateLimiter 
            args:
              redis-rate-limiter.replenishRate: 1 # 令牌桶每秒填充速率
              redis-rate-limiter.burstCapacity: 100 # 令牌桶总容量
              key-resolver: "#{@pathKeyResolver}" # 使用 SpEL 表达式按名称引用 bean
```

## 熔断降级（系统未采用 hystrix熔断）

1、添加依赖。

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

2、配置需要熔断降级服务

```yml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
  cloud:
    gateway:
      routes:
        # 系统模块
        - id: flybirds-system
          uri: lb://flybirds-system
          predicates:
            - Path=/system/**
          filters:
            - StripPrefix=1
            # 降级配置
            - name: Hystrix
              args:
                name: default
                # 降级接口的地址
                fallbackUri: 'forward:/fallback'
```

提示

上面配置包含了一个`Hystrix`过滤器，该过滤器会应用`Hystrix`熔断与降级，会将请求包装成名为`fallback`的路由指令`RouteHystrixCommand`，`RouteHystrixCommand`继承于`HystrixObservableCommand`，其内包含了`Hystrix`的断路、资源隔离、降级等诸多断路器核心功能，当网关转发的请求出现问题时，网关能对其进行快速失败，执行特定的失败逻辑，保护网关安全。

配置中有一个可选参数`fallbackUri`，当前只支持`forward`模式的`URI`。如果服务被降级，请求会被转发到该`URI`对应的控制器。控制器可以是自定义的`fallback`接口；也可以使自定义的`Handler`，需要实现接口`org.springframework.web.reactive.function.server.HandlerFunction`。

3、实现添加熔断降级处理返回信息

```java
package com.flybirds.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.flybirds.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.util.Optional;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;

/**
 * 熔断降级处理
 * 
 * @author flybirds
 */
@Component
public class HystrixFallbackHandler implements HandlerFunction<ServerResponse>
{
    private static final Logger log = LoggerFactory.getLogger(HystrixFallbackHandler.class);

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest)
    {
        Optional<Object> originalUris = serverRequest.attribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        originalUris.ifPresent(originalUri -> log.error("网关执行请求:{}失败,hystrix服务降级处理", originalUri));
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(JSON.toJSONString(R.fail("服务已被降级熔断"))));
    }
}
```

4、路由配置信息加一个控制器方法用于处理重定向的`/fallback`请求

```java
package com.flybirds.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import com.flybirds.gateway.handler.HystrixFallbackHandler;
import com.flybirds.gateway.handler.ValidateCodeHandler;

/**
 * 路由配置信息
 * 
 * @author flybirds
 */
@Configuration
public class RouterFunctionConfiguration
{
    @Autowired
    private HystrixFallbackHandler hystrixFallbackHandler;

    @Autowired
    private ValidateCodeHandler validateCodeHandler;

    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction()
    {
        return RouterFunctions
                .route(RequestPredicates.path("/fallback").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        hystrixFallbackHandler)
                .andRoute(RequestPredicates.GET("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        validateCodeHandler);
    }
}
```

4、测试服务熔断降级
启动网关服务`flybirdsGatewayApplication.java`，访问`/system/*`在进行测试，会发现返回`服务已被降级熔断`，表示降级成功。

## 熔断降级（系统未采用Hystrix  而是Sentinel 熔断降级规则适配到naocs中心）

限流和熔断降级的实现均采用的是sentinel来适配，未采用hystrix熔断

主要的适配文件为：

```
sentinel:
          # 取消控制台懒加载
          eager: true
          transport:
            # 控制台地址
            dashboard: 192.168.144.129:8858
          # nacos配置持久化
          datasource:
          	#限流
            ds1:
              nacos:
                server-addr: 192.168.144.129:8848
                namespace: 6fb7c77b-c212-4e0e-99b3-b84448a1b994
                dataId: sentinel-flybirds-gateway
                groupId: DEFAULT_GROUP
                data-type: json
                rule-type: flow
            #降级处理
            ds2:
              nacos:
                server-addr: 192.168.144.129:8848
                namespace: 6fb7c77b-c212-4e0e-99b3-b84448a1b994
                dataId: sentinel-degrade-gateway
                groupId: DEFAULT_GROUP
                data-type: json
                rule-type: degrade
```

限流规则

```
[
    {
        "resource": "flybirds-system",
        "count": 500,
        "grade": 0,
        "timeWindow": 10,
        "minRequestAmount": 100,
        "statIntervalMs": 1000,
        "slowRatioThreshold": 0.5
    },
    {
        "resource": "flybirds-generate",
        "count": 500,
        "grade": 0,
        "timeWindow": 10,
        "minRequestAmount": 100,
        "statIntervalMs": 1000,
        "slowRatioThreshold": 0.5
    },
    {
        "resource": "flybirds-job",
        "count": 500,
        "grade": 0,
        "timeWindow": 10,
        "minRequestAmount": 100,
        "statIntervalMs": 1000,
        "slowRatioThreshold": 0.5
    },
    {
        "resource": "flybirds-sms",
        "count": 500,
        "grade": 0,
        "timeWindow": 10,
        "minRequestAmount": 100,
        "statIntervalMs": 1000,
        "slowRatioThreshold": 0.5
    },
    {
        "resource": "flybirds-media",
        "count": 500,
        "grade": 0,
        "timeWindow": 10,
        "minRequestAmount": 100,
        "statIntervalMs": 1000,
        "slowRatioThreshold": 0.5
    },
    {
        "resource": "flybirds-mangefile",
        "count": 500,
        "grade": 0,
        "timeWindow": 10,
        "minRequestAmount": 100,
        "statIntervalMs": 1000,
        "slowRatioThreshold": 0.5
    },
    {
        "resource": "flybirds-oauth",
        "count": 500,
        "grade": 0,
        "timeWindow": 10,
        "minRequestAmount": 100,
        "statIntervalMs": 1000,
        "slowRatioThreshold": 0.5
    }
]
```

#### 继承实现 AbstractGatewayFilterFactory工厂过滤器，是需要进行制定路由规则的

列如：CacheRequestFilter与VerificationCodeFilter

```
filters:
    # 验证码处理
    - CacheRequestFilter
    - VerificationCodeFilter
```

#### 

# nacos的动态刷新原理

Nacos用了 push 还是 pull模式 ？

nacos的模式是采用的客户端主动pull的。

原文地址：https://mp.weixin.qq.com/s/yTWtDO4l_PUINj_1Bes-fw

### 推与拉模型

客户端与配置中心的数据交互方式其实无非就两种，要么推`push`，要么拉`pull`。

**推模型**

客户端与服务端建立`TCP`长连接，当服务端配置数据有变动，立刻通过建立的长连接将数据推送给客户端。

优势：长链接的优点是实时性，一旦数据变动，立即推送变更数据给客户端，而且对于客户端而言，这种方式更为简单，只建立连接接收数据，并不需要关心是否有数据变更这类逻辑的处理。

弊端：长连接可能会因为网络问题，导致不可用，也就是俗称的`假死`。连接状态正常，但实际上已无法通信，所以要有的心跳机制`KeepAlive`来保证连接的可用性，才可以保证配置数据的成功推送。

**拉模型**

客户端主动的向服务端发请求拉配置数据，常见的方式就是轮询，比如每3s向服务端请求一次配置数据。

轮询的优点是实现比较简单。但弊端也显而易见，轮询无法保证数据的实时性，什么时候请求？间隔多长时间请求一次？都是不得不考虑的问题，而且轮询方式对服务端还会产生不小的压力。

**nacos采用的是长轮询**

长轮询可不是什么新技术，它不过是由服务端控制响应客户端请求的返回时间，来减少客户端无效请求的一种优化手段，其实对于客户端来说与短轮询的使用并没有本质上的区别。

客户端发起请求后，服务端不会立即返回请求结果，而是将请求挂起等待一段时间，如果此段时间内服务端数据变更，立即响应客户端请求，若是一直无变化则等到指定的超时时间后响应请求，客户端重新发起长链接。

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHsia2GQuUW1zf7PqcW0BxldGjTvRzvoib6u3icpq08D3QDQ5phs4j7lmXyw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



 `Nacos`配置中心的几个核心概念：`dataId`、`group`、`namespace`，它们的层级关系如下图 

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHs4c5oNzHcib4DY3SsI5aTOC0FKHhXczKqh3ex1TichIWsbQI1LbmDxQWQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

nacos的设计模式

下图简要描述了`nacos`配置中心的架构流程。

客户端、控制台通过发送Http请求将配置数据注册到服务端，服务端持久化数据到Mysql。

客户端拉取配置数据，并批量设置对`dataId`的监听发起长轮询请求，如服务端配置项变更立即响应请求，如无数据变更则将请求挂起一段时间，直到达到超时时间。为减少对服务端压力以及保证配置中心可用性，拉取到配置数据客户端会保存一份快照在本地文件中，优先读取

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHsbFDOPcN6LQnr3piascbNjuia4MJ3gvyeoLy9VBDDquXQN8ee7QEv0xkw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**获取配置**

 `Nacos`获取配置数据的逻辑比较简单，先取本地快照文件中的配置，如果本地文件不存在或者内容为空，则再通过HTTP请求从远端拉取对应dataId配置数据，并保存到本地快照中，请求默认重试3次，超时时间3s。 

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHshcNsQicPoFSFz6lBWfeZA6ibrXvdtII3ZpGzKF1A5Avsibu8UUibzVlGiaA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**注册监听**

 客户端注册监听，先从`cacheMap`中拿到`dataId`对应的`CacheData`对象。 

```
public void addTenantListenersWithContent(String dataId, String group, String content,
                     List<? extends Listener> listeners) throws NacosException {
  group = blank2defaultGroup(group);
  String tenant = agent.getTenant();
  *// 1、获取dataId对应的CacheData，如没有则向服务端发起长轮询请求获取配置*
  CacheData cache = addCacheDataIfAbsent(dataId, group, tenant);
  synchronized (cache) {
    *// 2、注册对dataId的数据变更监听*
    cache.setContent(content);
    for (Listener listener : listeners) {
      cache.addListener(listener);
    }
    cache.setSyncWithServer(false);
    agent.notifyListenConfig();
  }
} 
```



 如没有则向服务端发起长轮询请求获取配置，默认的`Timeout`时间为30s，并把返回的配置数据回填至`CacheData`对象的content字段，同时用content生成MD5值；再通过`addListener()`注册监听器。 

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHsmbGI8zbW7GFdAHnnIwJLOF5peLBcicIkN7ic7KfXYIqiby59p1oibcCXjw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

 `CacheData`也是个出场频率非常高的一个类，我们看到除了dataId、group、tenant、content这些相关的基础属性，还有几个比较重要的属性如：`listeners`、`md5`(content真实配置数据计算出来的md5值)，以及注册监听、数据比对、服务端数据变更通知操作都在这里 

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHseXiaMmn4ts1Jgic7aDRRiaN6M8W2HtbrC0bcj2de5ibfILjGtbev8KnjQw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

其中 `listeners`是对dataId所注册的所有监听器集合 ，其中的`ManagerListenerWrap`对象除了持有`Listener`监听类，还有一个`lastCallMd5`字段，这个属性很关键，它是判断服务端数据是否更变的重要条件。

在添加监听的同时会将`CacheData`对象当前最新的md5值赋值给`ManagerListenerWrap`对象的`lastCallMd5`属性。

**变更通知**

 我们还是从头看，`NacosConfigService`类的构造器中初始化了一个`ClientWorker`，而在`ClientWorker`类的构造器中又启动了一个线程池来轮询`cacheMap`。 

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHsYcdNeMdrPR9ejwsZicicSe0Hd9aJfcYXa3J2muoTDe0kc8jnmfO6HfPQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

 而在`executeConfigListen()`方法中有这么一段逻辑，检查`cacheMap`中dataId的`CacheData`对象内，MD5字段与注册的监听`listener`内的`lastCallMd5值`，不相同表示配置数据变更则触发`safeNotifyListener`方法，发送数据变更通知。

```java
void checkListenerMd5() {
    for (ManagerListenerWrap wrap : listeners) {
        if (!md5.equals(wrap.lastCallMd5)) {
            safeNotifyListener(dataId, group, content, type, md5, encryptedDataKey, wrap);
        }
    }
}
```

 `safeNotifyListener()`方法单独起线程，向所有对`dataId`注册过监听的客户端推送变更后的数据内容。 

![图片](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aNicyI1smgmHqolM7YQsiapHsmDgu7RNZfjDt8DD8QwOOUk3x8tZqctWCN1mp1fZNBsj8nTItdMQZbQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



 客户端接收通知，直接实现`receiveConfigInfo()`方法接收回调数据，处理自身业务就可以了。 

总结nacos数据中的pull配置的基本原理

1. 客户端使用的长轮询的方式会发送一个携带groupkey的http请求给与nacos的服务端，在nacos的配置中维护一个cacheMap的原子集合，key为dataId+gourpId+ tenant(租户) 组成的字符串，value为一个cacheData数据，数据中维护一个一系列的字段。
2. nacos客户端通过 getConfigAndSignListener ()请求配置信息，并且注册监听
3. 注册监听，主要是包含了先从 本地cacheMap 中获取大content内容，如果不存在的话，发送http长轮询操作进行请求操作，默认的时间为30秒，默认将获取到的信息返回cacheMap 中的content,同时会生成一个MD5的加密钥，在通过 addListener 进行注册监听
4. cacheData中包含了 dataId,groupId,tenant,content,md5 等属性，还有一个 listeners 这个时对CacheData注册监听的集合, ManagerListenerWrap除了持有listener这属性，还有一个 lastCallMd5 的值， 在添加监听的同时会将`CacheData`对象当前最新的md5值赋值给`ManagerListenerWrap`对象的`lastCallMd5`属性 
5. 如何变更通知的呢？时因为在初始化nacosconfigService中存在listenerWork的工作，里面启动了一个线程池的轮询cachemap ,里面有一个方法 executeConfigListen （），检测cache中的MD5值与注册监听的lastcallMD5值是否相等, 不相同表示配置数据变更则触发`safeNotifyListener`方法，发送数据变更通知,`safeNotifyListener()`方法单独起线程，向所有对`dataId`注册过监听的客户端推送变更后的数据内容,  客户端接收通知，直接实现`receiveConfigInfo()`方法接收回调数据，处理自身业务就可以了。 

