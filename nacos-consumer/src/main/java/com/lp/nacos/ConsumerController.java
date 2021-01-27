package com.lp.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    private Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private ConsumerClient consumerClient;

    @RequestMapping("/get")
    public String get() {
        return consumerClient.get();
    }

}
