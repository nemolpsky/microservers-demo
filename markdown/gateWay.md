Spring Cloud GateWay是一个网关框架，可以转发处理请求。使用起来也很简单，按照官方的文档说法，提供了许多各种各样的工厂来创造拦截器，比如根据请求方法类型，请求地址，请求头或者Cookie等等，所以既可以在代码中配置，也可以在配置文件中配置，详细配置都在Gateway项目下的配置文件中。

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```