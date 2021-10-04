package com.lp.nacos;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableEncryptableProperties
@Slf4j
public class NacosServer1Application {

    public static void main(String[] args) {
        SpringApplication.run(NacosServer1Application.class, args);
    }

}
