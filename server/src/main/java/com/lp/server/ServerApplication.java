package com.lp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ServerApplication {

    private Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    public DiscoveryClient client;

    public static ServerApplication instance;
    public static Logger loggerInstance;

    @PostConstruct
    public void init() {
        instance = this;
        loggerInstance = logger;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

        instance.client.getInstances("server").forEach(e ->{
            loggerInstance.info("instanceId is {},uri is {},", e.getInstanceId(),e.getUri());
        });
    }

}
