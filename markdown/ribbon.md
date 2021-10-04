Ribbon是一个可以实现负载均衡的组件，Feign默认会使用Ribbon，Ribbon客户端的名字会很Feign客户端名字一样。如果想要配置全局Ribbon，可以使用下列的配置。

CustomRule是选择策略，这里简单实现了一个策略，永远都只选择服务列表中第一个服务。
```
@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
public class RibbonClientDefaultConfigurationTestsConfig {
}

@Configuration
public class DefaultRibbonConfig {
    @Bean
    public IRule ribbonRule() {
        return new CustomRule();
    }

}
```

分别启动两个Server，端口是10003和10005，观察日志可以看到每次请求，都会调用10005端口，默认是采用轮询。

```
2021-01-10 12:02:49.923  INFO 6664 --- [io-10002-exec-1] com.lp.client.CustomRule                 : invoke choose method, and serverList is [Half-PC.mshome.net:10003, Half-PC.mshome.net:10005].
2021-01-10 12:02:49.942 DEBUG 6664 --- [io-10002-exec-1] com.lp.client.UserClient                 : [UserClient#findText] <--- HTTP/1.1 200 (188ms)
2021-01-10 12:02:51.894 DEBUG 6664 --- [io-10002-exec-5] com.lp.client.UserClient                 : [UserClient#findText] ---> GET http://user-server/user/server/findText HTTP/1.1
2021-01-10 12:02:51.894  INFO 6664 --- [io-10002-exec-5] com.lp.client.CustomRule                 : invoke choose method, and serverList is [Half-PC.mshome.net:10003, Half-PC.mshome.net:10005].
2021-01-10 12:02:51.899 DEBUG 6664 --- [io-10002-exec-5] com.lp.client.UserClient                 : [UserClient#findText] <--- HTTP/1.1 200 (4ms)
2021-01-10 12:02:52.858 DEBUG 6664 --- [io-10002-exec-6] com.lp.client.UserClient                 : [UserClient#findText] ---> GET http://user-server/user/server/findText HTTP/1.1
2021-01-10 12:02:52.858  INFO 6664 --- [io-10002-exec-6] com.lp.client.CustomRule                 : invoke choose method, and serverList is [Half-PC.mshome.net:10003, Half-PC.mshome.net:10005].
2021-01-10 12:02:52.860 DEBUG 6664 --- [io-10002-exec-6] com.lp.client.UserClient                 : [UserClient#findText] <--- HTTP/1.1 200 (2ms)
```

更多配置可以参考[官网](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-ribbon.html)。
