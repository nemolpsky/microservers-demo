package com.lp.client;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import static java.util.concurrent.TimeUnit.SECONDS;

//@Configuration 添加该配置是全局生效，不添加则需要在feign客户端上使用注解字段手动引入
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }


    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100,SECONDS.toMillis(1),3);
    }
}
