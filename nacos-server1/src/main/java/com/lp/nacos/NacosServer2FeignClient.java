package com.lp.nacos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "nacos-server2", path = "/server2")
public interface NacosServer2FeignClient {

    @RequestMapping(value = "/get")
    String get();
}
