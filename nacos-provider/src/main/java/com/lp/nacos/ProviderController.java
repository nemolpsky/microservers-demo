package com.lp.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    private Logger logger = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    Environment environment;


    @Value("${test.name:null value}")
    private String testName;

    @RequestMapping("/get")
    public String get() {
        String value = environment.getProperty("test.name");
        System.out.println(value);
        System.out.println(testName);
        return "This is Nacos-Provider message, and value is " + value;
    }

}
