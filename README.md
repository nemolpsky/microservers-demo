# feign-demo

### 1. feign使用
feign本质上就是一个封装好的注解式HTTP请求框架，微服务架构中使用feign很方便，需要添加下列依赖。

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

调用方，也就是客户端client需要添加下列注解在启动类上。

```
@EnableFeignClients
```

在client中创建一个接口使用@FeignClient注解，设置名字和路径即可，它也支持直接继承别的接口，便于分组。

```
public interface UserService {
    @RequestMapping(value = "/find")
    User find();
}

@FeignClient(value = "server",path = "/user/server", fallbackFactory  = Fallback.class,configuration = FeignLogConfig.class)
public interface UserClient extends UserService{

    @RequestMapping(value = "/get")
    User get();
}
```

服务端server中提供对应地址的实现方法调用即可，本质上就是用feign简化调用的过程。
所以上面客户端的path属性对应服务端中的路径名，value则是对应服务名。

```
@RestController()
@RequestMapping(value = "/user/server")
public class UserServer {
    private Logger logger = LoggerFactory.getLogger(UserServer.class);

    @RequestMapping(value = "")
    User index(){
        logger.info("call server index");
        return new User(0);
    }
}
```

feign自带封装好的日志输出，yml配置文件中先配置client接口的日志路径，再添加FeignLogConfig
文件，配置输出的级别，默认是None不输出，还有另外三个级别。要使用configuration属性使用这个
配置类，配置文件的优先级比Bean要高，修改feign.client.default-to-properties参数可以颠倒优先级。
注意这个配置类添加@Configuration就会自动配置到全局范围呢。
```
logging.level.com.lp.client.UserClient: debug

@Bean
Logger.Level feignLoggerLevel(){
    return Logger.Level.BASIC;
}

@FeignClient(value = "server",path = "/user/server", fallbackFactory  = Fallback.class,configuration = FeignLogConfig.class)
```

还有自带的注解@SpringQueryMap，对象传参又不想用json格式，可以用这个注解。
```
@RequestMapping(value = "/findByAge")
User findByAge(@SpringQueryMap Param param);
```

---

### 2. Hystrix使用
feign里面已经集成了hystrix，不需要额外的依赖，需要下面配置打开，还可以配置超时时间。一旦打开，所有feign
客户端都被包了一个熔断器。
```
feign:
  client:
    config:
# default表示，全局配置，单个配置使用feign的value值代替default
      default:
        connectTimeout: 2000
        readTimeout: 2000
  hystrix:
    enabled: true
hystrix:
  command:
# default表示，全局配置，单个配置使用feign的value值代替default
    default:
      execution:
        timeout:
          enabled: true
        isolation:
          thread:
            timeoutInMilliseconds: 1000
```

可以专门给一个feign客户端配置自己的断路器，直接新建一个FallbackFactory，或者新建一个Fallback
都行。
```
@FeignClient(value = "server",path = "/user/server", fallbackFactory  = Fallback.class)
@FeignClient(value = "server",path = "/user/server", fallback  = Fallback2.class)
```

单独想关闭一个feign客户端的熔断器，配置下列的代码即可。
```
@Configuration
public class FooConfiguration {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
```

Hystrix提供了一个监控仪表盘hystrix dashboard，其实就是一个单独的服务，在hystrix模块中添加
下面的依赖，启动类使用@EnableHystrixDashboard注解，还需要在配置文件中
配置代理允许访问的host列表。
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

本质上是提供代理去访问服务自带的监控，所以想要用仪表盘监控client项目，需要
添加下面的监控依赖，配置文件中配置hystrix的访问页面。client还必须添加额外的
hystrix依赖，使用额外的注解。最后先访问http://localhost:10004/hystrix，再通过
界面代理访问http://localhost:10002/actuator/hystrix.stream。
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

也可以直接使用@HystrixCommand注解配置Hystrix，参数可以参考Hystrix在Github
上官方提供的配置文档。

```
@HystrixCommand(fallbackMethod = "errorReturn",groupKey = "key1",commandKey = "key1"
    , commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "1")
     }
)
```

