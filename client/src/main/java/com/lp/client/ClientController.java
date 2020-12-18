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

    @GetMapping("/findByList")
    public User findByList(){
        Random random = new Random();
        Map<String, List<User>> map = new HashMap<>();

        int i=0;
        while (i<5){
            List<User> list = new ArrayList<>();
            User user1 = new User(random.nextInt(15));
            User user2 = new User(random.nextInt(15));
            User user3 = new User(random.nextInt(15));
            list.add(user1);
            list.add(user2);
            list.add(user3);
            map.put(i+"",list);
            i++;
        }
        return userClient.findByList(map);
    }
}
