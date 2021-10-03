package com.lp.nacos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "nacos-provider", path = "/server")
public interface ConsumerClient {

    @RequestMapping(value = "/get")
    String get();
}
