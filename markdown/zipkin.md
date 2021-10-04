Zipkin是一个分布式的链路追踪框架，是由Twitter开源的，还是比较值得信赖的，它可以记录分析在分布式服务中整个请求的链路耗时，以便分析瓶颈。Spring Cloud也集成了，只要添加依赖就可以使用，直接在父项目中添加下面两个依赖，所有的微服务都会记录追踪信息。

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```

当然还需要添加一个可视化界面，官网有很多种方式，最简单的方式是使用Docker镜像，请求接口后就可以看到在每个服务上调用所花的时间。当然这只是一个最简单的例子，真实使用还可以使用消息队列，还可以持久化追踪信息。

```
docker pull openzipkin/zipkin:2.23-arm64
docker run --name zipkin -d -p 9411:9411 openzipkin/zipkin:2.23-arm64
```

如果是本地玩玩的话什么配置都不用加，默认就是把追踪消息发到本地的ip，但是如果实际使用至少需要指定zipkin的ip和端口，还有追踪信息的持久化配置

```
spring
  zipkin:
    base-url: http://192.168.1.104:9411
```