Jasypt是一个解决配置文件脱敏的框架，比如配置文件中的一些密码和敏感信息。[官网](http://www.jasypt.org/)比较长时间没更新，但是[Github仓库](https://github.com/ulisesbocchio/jasypt-spring-boot)倒是已经发布了新版本。支持Spring，也支持Spring Boot，其实本质上就是使用公钥和特定的加密方式获取一个加密字符串，由于每次加密出来的都不一样，但是Jasypt能解决，这就解决了配置文件中的敏感信息了。


SpringBoot直接引入下面的依赖。
```
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.4</version>
</dependency>
```

再使用下面的注解。

```
@EnableEncryptableProperties
public class GatewayApplication {
```


配置文件中声明公钥，和加密方式，因为新版本的默认加密方式改了，所以要特别指定。再使用```ConfigEncryptUtils```类来生成加密后的密文，将密文替换原有的明文即可，只不过要使用```ENC()```包裹，上面指定加密方式就是确保工具类和框架加解密用的同样的方法，不然肯定是解密失败的。
```
jasypt:
  encryptor:
    password: gateway-public-key
    algorithm: PBEWithMD5AndDES
    iv-generator-classname: org.jasypt.iv.NoIvGenerator
...:
  ...:
    ...: 
      username: ENC(F3KUstYYrVCbp+dBwFkWXNISkI8BpOw6)
      password: ENC(lNEp2eRLAgzgO6x3Djhvrw==)
```