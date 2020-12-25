package com.lp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "system",path = "/system/server", fallback = Fallback2.class,configuration = FeignConfig.class,qualifier = "systemClient")
public interface SystemClient{

    @RequestMapping(value = "/get")
    User get();

}
