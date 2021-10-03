package com.lp.nacos;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Slf4j
public class CommonController {

    @Value("${message.text:registry failed}")
    private String nacosMessage;

    @RequestMapping("/server2/get")
    public String get() {
        log.info("This is nacos message:{}",nacosMessage);
        return null;
    }

}
