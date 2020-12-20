package com.lp.client;

import feign.Logger;
import org.springframework.context.annotation.Bean;

//@Configuration 添加该配置是全局生效，不添加则需要在feign客户端上使用注解字段手动引入
public class FeignLogConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.BASIC;
    }
}
