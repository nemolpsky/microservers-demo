package com.lp.nacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class NacosServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(NacosServer2Application.class, args);
    }
}
