package com.lp.nacos;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    private NacosServer2FeignClient nacosServer2FeignClient;

    @RequestMapping("/server1/get")
    public String get() {
        log.info("nacos server1 get.");
        return nacosServer2FeignClient.get();
    }

}
