package com.lp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController()
@RequestMapping(value = "/user/server")
//@RequestMapping("server")
public class UserServer {
    private Logger logger = LoggerFactory.getLogger(UserServer.class);

    @RequestMapping(value = "")
    User index(){
        logger.info("call server index");
        return new User(0);
    }

    @RequestMapping(value = "/get")
    User get(){
        logger.info("call /user/server/get");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User(11);
    }

    @RequestMapping(value = "/find")
    User find(){
        logger.info("call /user/server/find");
        return new User(12);
    }

    @RequestMapping(value = "/findByAge")
    public User findByAge(Param param){
        logger.info("call /user/server/findByAge, param is {}.",param);
        return new User(param.getAge1()+param.getAge2());
    }
}
