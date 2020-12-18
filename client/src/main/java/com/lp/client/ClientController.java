package com.lp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ClientController {
    @Autowired
    private UserClient userClient;

    @GetMapping("/get")
    public User getUser(){
        return userClient.get();
    }

    @GetMapping("/find")
    public User findUser(){
        return userClient.find();
    }

    @GetMapping("/findByAge")
    public User findByAge(){
        Random random = new Random();
        Param param = new Param();
        param.setAge1(random.nextInt(15));
        param.setAge2(random.nextInt(15));
        return userClient.findByAge(param);
    }
}
