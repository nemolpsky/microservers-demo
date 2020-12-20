package com.lp.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class ClientController {

    private Logger logger = LoggerFactory.getLogger(ClientController.class);


    @Autowired
    private UserClient userClient;

    @Qualifier("system")
    private SystemClient systemClient;

    // 注意，定义的降级方法签名需要保持一致
    @HystrixCommand(fallbackMethod = "errorReturn", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    @GetMapping("/hystrixGet")
    public User hystrixGet(Integer time) throws Exception {
        time = time==null?0:time;
        logger.info("hystrixGet step 1");
        if (time>0){
            logger.info("hystrixGet step 2");
            TimeUnit.SECONDS.sleep(time);
        }
        logger.info("hystrixGet step 3");
        return new User(111);
    }

    @GetMapping("/get")
    public User getUser() {
        return userClient.get();
    }

    @GetMapping("/find")
    public User findUser() {
        return userClient.find();
    }

    @GetMapping("/findByAge")
    public User findByAge() {
        Random random = new Random();
        Param param = new Param();
        param.setAge1(random.nextInt(15));
        param.setAge2(random.nextInt(15));
        return userClient.findByAge(param);
    }

    public User errorReturn(Integer i){
        return new User(-100);
    }
}
