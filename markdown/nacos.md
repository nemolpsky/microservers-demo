Nacos是阿里的开源注册中心，可以选择是支持AP还是CP，除了像Eureka那样提供服务注册的功能之外，还自带管理界面，使用起来更加方便。

添加下面的依赖就可以使用，但是要注意，对版本是有要求的，Spring Cloud，Spring Cloud Alibaba和Nacos依赖的版本都是有要求的，详细的可以查看[官网](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)，本项目是Spring Cloud Hoxton.SR9，Spring Cloud Alibaba 2.2.6.RELEASE。

切记一定要版本对的上，不然各种乱七八糟的异常都会有，国内的开源框架在版本兼容和文档方面一直做的挺糟糕的。
```
    <dependencies>
        <!-- 发现服务 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.6.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

启动类打个注解，访问 http://localhost:8848/nacos/index.html 就可以看到Nacos的控制台服务列表里面有数据了。总的来说很简单，功能也比较齐全，还自带管理界面，减少了运维成本，不过文档和例子比起Spring还是少了很多，质量也差了很多。
```
@EnableDiscoveryClient
```

除此之外Nacos还可以直接当作配置中心，需要添加下面的依赖。

```
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>2.1.0.RELEASE</version>
</dependency>
```

需要注意的就是如何正确匹配路径，Nacos既然可以作为配置中心那肯定会存储多个服务的配置文件，这就需要一套命名规范来匹配。首先是需要一个```bootstrap.yml```配置文件，这和使用Spring Cloud Config一样，bootstrap.yml是专门用来设置配置中心的一些属性，会比application.yml提前加载，并且会覆盖前者的属性。

Nacos的命名规则是下列这个格式，其中prefix对应spring.application.name的值，也可以通过配置spring.cloud.nacos.config.prefix来修改，中间的则是环境名称，例如dev等。最后一个则是对应的文件格式，比如yaml。也就是说Nacos的配置文件名字要和这个规则对的上才能找到对应的配置文件。

```
${prefix}-${spring.profiles.active}.${file-extension}
```

还有就是动态刷新配置了，Controller层需要添加@RefreshScope注解，使用以前正常的注入配置文件属性的方式即可。
```
@RefreshScope
public class ProviderController {
    ...

    @Value("${name:empty}")
    private String value;
}
```

下面是在Nacos中配置了三个配置文件，nacos-provider.yaml不配置profiles的情况，nacos-provider就是上面说的应用名，前两个则分别对应dev和test环境。
按照配置文件中激活不同的环境就会读取不同的配置文件。

![nacos1](./images/nacos1.png)

Nacos中有命名空间的概念，可以理解为分组的意思，注册中心和配置中心都可以配置命名空间，如果是配置中心则一定要对应正确的命名空间才能读取到，如果是注册中心则一定是在同一个命名空间下才会请求到别的服务。

注意下面的两张图，在配置文件中填写的是命名空间的id才能生效，填写命名空间的名字是没效果的，如果命名空间不存在也可以注册成功，但是控制台不显示注册的服务，因为根本就没有对应的tag页面。

![nacos2](./images/nacos2.png)
![nacos3](./images/nacos3.png)


此外nacos的鉴权是默认关闭的，至少1.4.2版本是，需要手动设置配置打开，其实就是需要登录控制台的账户名和密码，具体可以参考[官网](https://nacos.io/zh-cn/docs/auth.html)。

```
# 非Docker部署下，配置文件配置
nacos.core.auth.enabled=true
# Docker部署下，设置环境变量
NACOS_AUTH_ENABLE=true
```

如果想使用Mysql持久化还需要提前[初始化脚本](https://github.com/alibaba/nacos/blob/develop/distribution/conf/nacos-mysql.sql)，在配置文件中设置好Mysql的配置项。如果是docker可以使用docker-compose的方式部署，也可以自己去docker hub拉镜像[手动启动](https://gist.github.com/nemolpsky/a4ada5507b6f31bc39c7b861c4957676)。
