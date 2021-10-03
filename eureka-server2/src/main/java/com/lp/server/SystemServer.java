package com.lp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/system/server")
public class SystemServer {
    private Logger logger = LoggerFactory.getLogger(SystemServer.class);

    @RequestMapping(value = "/get")
    User get(){
        logger.info("call /system/server/get");
        return new User(11);
    }
}
