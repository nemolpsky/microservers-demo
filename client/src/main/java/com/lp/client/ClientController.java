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
    @HystrixCommand(fallbackMethod = "errorReturn",groupKey = "key1",commandKey = "key2"
            , commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
//                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
//                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "1")
                }
            )
    @GetMapping("/hystrixGet1")
    public User hystrixGet1(Integer time) throws Exception {
        time = time==null?0:time;
        logger.info("hystrixGet1 step 1，thread is {}",Thread.currentThread().getName());
        if (time>0){
            int i = 1/0;
            logger.info("hystrixGet step 1");
            TimeUnit.SECONDS.sleep(time);
        }
        logger.info("hystrixGet step 1");
        return new User(111);
    }

    // 注意，定义的降级方法签名需要保持一致
    @HystrixCommand(fallbackMethod = "errorReturn",groupKey = "key2",commandKey = "key2"
            , commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
//                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
//                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "1")
            },threadPoolProperties = {
//            @HystrixProperty(name = "maximumSize", value = "1"),
            @HystrixProperty(name = "coreSize", value = "1"),
//            @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "1")
    }
    )
    @GetMapping("/hystrixGet2")
    public User hystrixGet2(Integer time) throws Exception {
        time = time==null?0:time;
        logger.info("hystrixGet2 step 1，thread is {}",Thread.currentThread().getName());
        if (time>0){
            logger.info("hystrixGet2 step 2");
            TimeUnit.SECONDS.sleep(time);
        }
        logger.info("hystrixGet2 step 3");
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
