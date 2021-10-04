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

服务端server中提供对应地址的实现方法调用即可，本质上就是用feign简化调用的过程。所以上面客户端的path属性对应服务端中的路径名，value则是对应服务名。

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

feign自带封装好的日志输出，yml配置文件中先配置client接口的日志路径，再添加FeignLogConfig文件，配置输出的级别，默认是None不输出，还有另外三个级别。要使用configuration属性使用这个配置类，配置文件的优先级比Bean要高，修改feign.client.default-to-properties参数可以颠倒优先级。注意这个配置类添加@Configuration就会自动配置到全局范围。
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

还可以添加重试，和上面日志一样的配置，一样的导入即可。全局的话在配置类上加@Configuration注解。
```
@Bean
public Retryer feignRetryer() {
    return new Retryer.Default(100,SECONDS.toMillis(1),3);
}
```