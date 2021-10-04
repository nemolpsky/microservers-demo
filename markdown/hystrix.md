feign里面已经集成了hystrix，不需要额外的依赖，需要下面配置打开，还可以配置超时时间。一旦打开，所有feign客户端都被包了一个熔断器。
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

可以专门给一个feign客户端配置自己的断路器，直接新建一个FallbackFactory，或者新建一个Fallback都行。
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

Hystrix提供了一个监控仪表盘hystrix dashboard，其实就是一个单独的服务，在hystrix模块中添加下面的依赖，启动类使用@EnableHystrixDashboard注解，还需要在配置文件中配置代理允许访问的host列表。
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

本质上是提供代理去访问服务自带的监控，所以想要用仪表盘监控client项目，需要添加下面的监控依赖，配置文件中配置hystrix的访问页面。client还必须添加额外的hystrix依赖，使用额外的注解。最后先访问http://localhost:10004/hystrix，再通过界面代理访问 http://localhost:10002/actuator/hystrix.stream 。
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

也可以直接使用@HystrixCommand注解配置Hystrix，参数可以参考Hystrix在Github上官方提供的配置文档。

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