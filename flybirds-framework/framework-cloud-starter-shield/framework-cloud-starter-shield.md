# framework-cloud-starter-shield

framework-cloud-starter-shield是工程的中护盾模式

- 接口的幂等性
- 熔断与限流
- 分布式锁

熔断与限流采用的sentinel，阿里巴巴的开源组件

### 定义资源

 **资源**是`Sentinel`中的核心概念之一。我们说的资源，可以是任何东西，服务，服务里的方法，甚至是一段代码。最常用的资源是我们代码中的Java方法。`Sentinel`提供了`@SentinelResource`注解用于定义资源，并提供了`AspectJ`的扩展用于自动定义资源、处理`BlockException`等 

> 官网文档：https://github.com/alibaba/Sentinel/wiki/如何使用#定义资源

###   属性说明

```java
@Service
public class IUserServiceImpl implements IUserService
{
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @SentinelResource(value = "selectUserByName", blockHandler = "selectUserByNameBlockHandler", fallback = "selectUserByNameFallback")
    @Override
    public Object selectUserByName(String username)
    {
        return restTemplate.getForObject("http://localhost:9201/user/info/" + username, String.class);
    }

    // 服务流量控制处理，参数最后多一个 BlockException，其余与原函数一致。
    public Object selectUserByNameBlockHandler(String username, BlockException ex)
    {
        System.out.println("selectUserByNameBlockHandler异常信息：" + ex.getMessage());
        return "{\"code\":\"500\",\"msg\": \"" + username + "服务流量控制处理\"}";
    }

    // 服务熔断降级处理，函数签名与原函数一致或加一个 Throwable 类型的参数
    public Object selectUserByNameFallback(String username, Throwable throwable)
    {
        System.out.println("selectUserByNameFallback异常信息：" + throwable.getMessage());
        return "{\"code\":\"500\",\"msg\": \"" + username + "服务熔断降级处理\"}";
    }

}
```

1

`@SentinelResource`注解包含以下属性：

| 参数               | 描述                                          |
| ------------------ | --------------------------------------------- |
| value              | 资源名称，必需项（不能为空）                  |
| entryType          | 资源调用方向，可选项（默认为`EntryType.OUT`） |
| resourceType       | 资源的分类                                    |
| blockHandler       | 对应处理`BlockException`的函数名称            |
| blockHandlerClass  | 处理类的`Class`对象，函数必需为`static`函数   |
| fallback           | 用于在抛出异常的时候提供`fallback`处理逻辑    |
| defaultFallback    | 用作默认的回退的方法                          |
| fallbackClass      | 异常类的`Class`对象，函数必需为`static`函数   |
| exceptionsToTrace  | 异常类跟踪列表（默认为Throwable.class）       |
| exceptionsToIgnore | 排除掉的异常类型                              |

注意：注解方式埋点不支持 private 方法。

## 流量规则

- 资源名： 唯一名称，默认请求路径
- 针对来源： Sentinel可以针对调用者进行限流，填写微服务名，默认default（不区分来源）
- 阈值类型/单机阈值：
  - QPS（每秒请求数量）：当调用该api的QPS达到阈值的时候，进行限流
  - 线程数：当调用该api的线程数达到阈值的时候，进行限流
- 是否集群： 不需要集群
- 流控模式：
  - 直接：api达到限流条件时，直接限流
  - 关联：当关联的资源达到限流阈值时，就限流自己
  - 链路：只记录指定链路上的流量（指定资源从入口资源进来的流量，如果达到峰值，就进行限流）【api级别的针对来源】

- 流控效果：
  - 快速失败：直接失败，抛异常
  - Warm Up：根据coldFactor（冷加载因子，默认3）的值，从阈值/coldFactor，经过预热时长，才达到设置的QPS阈值
  - 排队等待：匀速排队，让请求以匀速通过，阈值类型必须设置为QPS，否则无效

### 代码定义

 理解上面规则的定义之后，我们可以通过调用`FlowRuleManager.loadRules()`方法来用硬编码的方式定义流量控制规则，比如： 

```java
private void initFlowQpsRule() {
    List<FlowRule> rules = new ArrayList<>();
    FlowRule rule = new FlowRule(resourceName);
    // set limit qps to 20
    rule.setCount(20);
    rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
    rule.setLimitApp("default");
    rules.add(rule);
    FlowRuleManager.loadRules(rules);
}
```

### 属性说明

流量控制规则(`FlowRule`)重要属性

| 参数            | 描述                                              | 描述                        |
| --------------- | ------------------------------------------------- | --------------------------- |
| resource        | 资源名，资源名是限流规则的作用对象                |                             |
| limitApp        | 流控针对的调用来源，若为 default 则不区分调用来源 | default，代表不区分调用来源 |
| grade           | 限流阈值类型，QPS 模式（1）或并发线程数模式（0）  | QPS 模式                    |
| count           | 限流阈值                                          |                             |
| strategy        | 调用关系限流策略：直接、链路、关联                | 根据资源本身（直接）        |
| controlBehavior | 流量控制效果(直接拒绝、Warm Up、匀速排队)         | 直接拒绝                    |
| clusterMode     | 是否集群限流                                      | 否                          |

同一个资源可以同时有多个限流规则，检查规则时会依次检查。

## 降级规则

 现代微服务架构都是分布式的，由非常多的服务组成。不同服务之间相互调用，组成复杂的调用链路。以上的问题在链路调用中会产生放大的效果。复杂链路上的某一环不稳定，就可能会层层级联，最终导致整个链路都不可用。因此我们需要对不稳定的弱依赖服务调用进行熔断降级，暂时切断不稳定调用，避免局部不稳定因素导致整体的雪崩。熔断降级作为保护自身的手段，通常在客户端（调用端）进行配置。 

### 代码定义

```java
private void initDegradeRule() {
    List<DegradeRule> rules = new ArrayList<>();
    DegradeRule rule = new DegradeRule();
    rule.setResource(KEY);
    // set threshold RT, 10 ms
    rule.setCount(10);
    rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
    rule.setTimeWindow(10);
    rules.add(rule);
    DegradeRuleManager.loadRules(rules);
}
```

### 属性说明

熔断降级规则(`DegradeRule`)重要属性

| 参数               | 描述                                                         | 描述       |
| ------------------ | ------------------------------------------------------------ | ---------- |
| resource           | 资源名，即规则的作用对象                                     |            |
| grade              | 熔断策略，支持慢调用比例/异常比例/异常数策略                 | 慢调用比例 |
| count              | 慢调用比例模式下为慢调用临界 RT（超出该值计为慢调用）；异常比例/异常数模式下为对应的阈值 |            |
| timeWindow         | 熔断时长，单位为 s                                           |            |
| minRequestAmount   | 熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入） | 5          |
| statIntervalMs     | 统计时长（单位为 ms），如 60*1000 代表分钟级（1.8.0 引入）   | 1000 ms    |
| slowRatioThreshold | 慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入）           |            |

同一个资源可以同时有多个降级规则。

### json适配

- 适配本地路径

```yml
spring:
  cloud:
    sentinel:
      datasource:
      	#限流
        ds1:
          file:
            file: classpath:flowRule.json
            data-type: json
            rule-type: flow
            spring:
        #降级
        ds1:
          file:
            file: classpath:degradeRule.json
            data-type: json
            rule-type: degrade
```

- 动态适配到nacos

sentinel`下面的`dataSource`中配置`nacos

```

spring: 
  application:
    # 应用名称
    name: flybuirds-xxxx 
  cloud:
    nacos:
      config:
        # 配置中心地址
        server-addr: 127.0.0.1:8848
        # 配置文件格式
        file-extension: yml
        # 共享配置
        shared-configs:
          - application-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}
    sentinel:
      # 取消控制台懒加载
      eager: true
      transport:
        # 控制台地址
        dashboard: 127.0.0.1:8718
      # nacos配置持久化
      datasource:
        ds1:
          nacos:
            server-addr: 127.0.0.1:8848
            dataId: sentinel-flybuirds-gateway
            groupId: DEFAULT_GROUP
            data-type: json
            rule-type: flow
```

适配降级服务于此一样

参考链接原理：https://zhuanlan.zhihu.com/p/272673691

关于fallback的异常处理链接地址：https://www.cnblogs.com/fanshuyao/p/14607028.html



### Sentinel整合OpenFeign

需要开启配置

\# 开启feign的限流支持

```
feign:

  sentinel:

   enabled: true
```



修改openfeign调用时限

 使用过openfeign的小伙伴都知道在用它调用其他微服务的时候，默认的超时时长是 1s;如何修改这个时长呢？

 在hystrix与openfeign整合的时候，需要调用端要开启feign对hystrix的支持，设置调用端hystrix的超时时长，还要设置ribbon的超时时长。在sentinel中，调用者要修改openfeign的默认超时时长，需要两点

- 开启feign对sentinel的支持
- 设置ribbon的超时时长

### Sentinel整合RestTemplate

`Spring Cloud Alibaba Sentinel`支持对`RestTemplate`调用的服务进行服务保护。需要在构造`RestTemplate Bean`时添加`@SentinelRestTemplate`注解。

`RestTemplate`添加`@SentinelRestTemplate`注解保护支持

**服务熔断处理类ExceptionUtil.java，必须使用静态方法。**

```
@Bean
@SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class, fallback = "fallback", fallbackClass = ExceptionUtil.class)
public RestTemplate restTemplate() {
	return new RestTemplate();
}
```

```
public class ExceptionUtil
{
    // 服务流量控制处理
    public static ClientHttpResponse handleException(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution, BlockException exception)
    {
        exception.printStackTrace();
        return new SentinelClientHttpResponse("{\"code\":\"500\",\"msg\": \"服务流量控制处理\"}");
    }

    // 服务熔断降级处理
    public static ClientHttpResponse fallback(HttpRequest request, byte[] body, ClientHttpRequestExecution execution,
            BlockException exception)
    {
        exception.printStackTrace();
        return new SentinelClientHttpResponse("{\"code\":\"500\",\"msg\": \"服务熔断降级处理\"}");
    }
}
```



#### 接口幂等性

```
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 幂等的超时时间，默认为 1 秒
     *
     * 注意，如果执行时间超过它，请求还是会进来
     */
    int timeout() default 1;
    /**
     * 时间单位，默认为 SECONDS 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 提示信息，正在执行中的提示
     */
    String message() default "重复请求，请稍后重试";

    /**
     * 使用的 Key 解析器
     */
    Class<? extends IdempotentKeyResolver> keyResolver() default DefaultIdempotentKeyResolver.class;
    /**
     * 使用的 Key 参数
     */
    String keyArg() default "";

}
```

定义一个注解类`Idempotent`幂等性的类，其中包括了，使用的`keyArg`参数，`keyResolver`的解析器，默认消息`message`，`timeout`接口幂等性的过期时间

使用aop中的注解（@Before("@annotation(idempotent)")）和@Aspect主键，表示如果使用@idempotent注解之前会进入下面的beforePointCut方法中

```
 @Before("@annotation(idempotent)")
    public void beforePointCut(JoinPoint joinPoint, Idempotent idempotent) {
        // 获得 IdempotentKeyResolver解析器
        IdempotentKeyResolver keyResolver = keyResolvers.get(idempotent.keyResolver());
        Assert.notNull(keyResolver, "找不到对应的 IdempotentKeyResolver解析器");
        // 解析 Key
        String key = keyResolver.resolver(joinPoint, idempotent);

        // 锁定 Key。
        boolean success = idempotentRedisDAO.setIfAbsent(key, idempotent.timeout(), idempotent.timeUnit());
        // 锁定失败，抛出异常
        if (!success) {
            log.info("[beforePointCut][方法({}) 参数({}) 存在重复请求]", joinPoint.getSignature().toString(), joinPoint.getArgs());
            throw new ServiceException(GlobalErrorCodeConstants.REPEATED_REQUESTS.getCode(), idempotent.message());
        }
    }
```

定义一个IdempotentKeyResolver接口类，其中实现一个默认的方法 `resolver`通过joinPoint参数和自定义注解idempotent解析对key值的解析操纵，也可以算是一种key加密规则操作吧。数据key解析以后就开始上锁操作，使用的是IdempotentRedisDAO上锁

在`IdempotentRedisDAO`中封装着RedisKeyDefine的key定义，

KeyTypeEnum类

```
  STRING("String"),
  LIST("List"),
  HASH("Hash"),
  SET("Set"),
  ZSET("Sorted Set"),
  STREAM("Stream"),
  PUBSUB("Pub/Sub");
```

TimeoutTypeEnum

```
 FOREVER(1), // 永不超时
 DYNAMIC(2), // 动态超时
 FIXED(3); // 固定超时
```

这一系列的都有可以进行封装进行日志转换操作，最终达到幂等性的操作。



## 分布式锁Redisson

###### 分布式可重入锁

 Redisson 的可重入锁（lock）是阻塞其他线程的，需要等待其他线程释放的。 

![图片](https://mmbiz.qpic.cn/mmbiz_png/SfAHMuUxqJ3DNHIhVd62c2tibSQIJXDWjqPhXjwV5m7boWcn67eicyKZibO0Diav4QPve0tcuvYRf5VWRh1p5OupAQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

如果服务停掉了，默认会有30秒的过期时间，会默认进行过期操作,在我们使用redis进行set上锁操作的，如果因为操作不当，没有设置锁的过期时间的话，就会在客户端挂掉的那一刻出现死锁的现场，在redisson中这则是提供一个看门狗的策略。

如果负责储存这个分布式锁的 Redisson 节点宕机以后，而且这个锁正好处于锁住的状态时，这个锁会出现锁死的状态。为了避免这种情况的发生，Redisson内部提供了一个监控锁的`看门狗`，它的作用是在Redisson实例被关闭前，不断的延长锁的有效期。

默认情况下，看门狗的检查锁的超时时间是30秒钟，也可以通过修改Config.lockWatchdogTimeout来另行指定。

如果我们未制定 lock 的超时时间，就使用 30 秒作为看门狗的默认时间。只要占锁成功，就会启动一个`定时任务`：每隔 10 秒重新给锁设置过期的时间，过期时间为 30 秒。

![图片](https://mmbiz.qpic.cn/mmbiz_png/SfAHMuUxqJ3DNHIhVd62c2tibSQIJXDWjQS5LvaKmSMokBgp3eMME9hJISW38lZKcPtkMXETRy3uEaz9O3WJxLA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

 当服务器宕机后，因为锁的有效期是 30 秒，所以会在 30 秒内自动解锁。（30秒等于宕机之前的锁占用时间+后续锁占用的时间）。 ![图片](https://mmbiz.qpic.cn/mmbiz_png/SfAHMuUxqJ3DNHIhVd62c2tibSQIJXDWjRlpC7KDhPoLQA0uvFhGUDeSdD1bazd6o2vFL90EctlQbFNrAN5wr8Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

######  设置锁过期时间

 lock.lock(8, TimeUnit.SECONDS); 

如果业务执行时间超过 8 秒，手动释放锁将会报错，如下图所示：

![图片](https://mmbiz.qpic.cn/mmbiz_png/SfAHMuUxqJ3DNHIhVd62c2tibSQIJXDWjDd808yS1ypeNZxOwfFtuIzsfVElbzDrkqDRtJxUazOdmkQ1CuoV0HQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

 所以我们如果设置了锁的自动过期时间，则执行业务的时间一定要小于锁的自动过期时间，否则就会报错。 

###### 分布式读写锁

基于 Redis 的 Redisson 分布式可重入读写锁`RReadWriteLock` Java对象实现了`java.util.concurrent.locks.ReadWriteLock`接口。其中读锁和写锁都继承了 `RLock`接口。

写锁是一个排他锁（互斥锁），读锁是一个共享锁。

- 读锁 + 读锁：相当于没加锁，可以并发读。
- 读锁 + 写锁：写锁需要等待读锁释放锁。
- 写锁 + 写锁：互斥，需要等待对方的锁释放。
- 写锁 + 读锁：读锁需要等待写锁释放

![图片](https://mmbiz.qpic.cn/mmbiz_png/SfAHMuUxqJ3DNHIhVd62c2tibSQIJXDWj7dVPQ35DB7XdYpXWfBDDYUNJBCfpFbPAyJskhQibzbA39N6smodVe2g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

读写锁在实际的业务场景中也出现过很多这样的情况

###### 分布式信号量

基于Redis的Redisson的分布式信号量（Semaphore）Java对象`RSemaphore`采用了与`java.util.concurrent.Semaphore`相似的接口和用法。同时还提供了异步（Async）、反射式（Reactive）和RxJava2标准的接口。

关于信号量的使用大家可以想象一下这个场景，有三个停车位，当三个停车位满了后，其他车就不停了。可以把车位比作信号，现在有三个信号，停一次车，用掉一个信号，车离开就是释放一个信号。

![图片](https://mmbiz.qpic.cn/mmbiz_png/SfAHMuUxqJ3DNHIhVd62c2tibSQIJXDWjtVFibQ17A15icO2qmHz1zyYw83W1v78q4ewFzgdg2jOGiaYn4v9l5vMsQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



参考文章：https://www.cnblogs.com/AnXinliang/p/10019389.html

参考文章：https://mp.weixin.qq.com/s/3bhVvJsHr_t5MFxzcmC7Jw

参考文章：https://zhuanlan.zhihu.com/p/135864820