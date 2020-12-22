package com.lp.client;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class ClientController {

    private Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private UserClient userClient;

    @Qualifier("system")
    private SystemClient systemClient;

//    @Autowired
//    private HystrixRequestLog requestLog;

    // 注意，定义的降级方法签名需要保持一致
    @CacheResult
    @HystrixCommand(fallbackMethod = "errorReturn",groupKey = "key1",commandKey = "key1"
            , commandProperties = {
            // 超时时间
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
            // 熔断的触发条件
//                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
//                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "1")
                }
            )
    @GetMapping("/hystrixGet1")
    public User hystrixGet1(@CacheKey Integer time) throws Exception {
        time = time==null?0:time;
        logger.info("hystrixGet1 step 1，thread is {}",Thread.currentThread().getName());
        if (time>0){
//            int i = 1/0;
            logger.info("hystrixGet step 2");
            TimeUnit.SECONDS.sleep(time);
        }
        logger.info("hystrixGet step 3");
        logger.info("log：{}", HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());
        return new User(111);
    }

    // 注意，定义的降级方法签名需要保持一致
    @HystrixCommand(fallbackMethod = "errorReturn",groupKey = "key2",commandKey = "key2"
            , commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
            },threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1")
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

    @GetMapping("/hystrixGet3")
    public User hystrixGet3() throws Exception {
//        HystrixRequestContext.initializeContext();
        logger.info("hystrixGet3 step 1，thread is {}",Thread.currentThread().getName());
        CacheCommand command1 = new CacheCommand(com.netflix.hystrix.HystrixCommand.Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("group3"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("key3")),new RestTemplate(),null);
        logger.info("result is {}.",command1.execute());
        // 调用清除缓存的方法，就不会读取缓存，而是真实的执行请求
        command1.flushRequestCache();
        CacheCommand command2 = new CacheCommand(com.netflix.hystrix.HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("group3"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("key3")),new RestTemplate(),null);
        logger.info("result is {}.",command2.execute());
        return null;
    }

    @GetMapping("/testGet")
    public String testGet() {
        logger.info("testGet , thread is {}.",Thread.currentThread().getName());
        return "test message.";
    }

    @GetMapping("/get")
    public User getUser() {
        return userClient.get();
    }

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @GetMapping("/find")
    public User findUser(HttpServletRequest request) {
//        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        request.setAttribute("param","paramObject");
        logger.info("param is {}.",request.getAttribute("param"));
        userClient.find();
        userClient.find();
        logger.info("log：{}", HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());
        return null;
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

    public String getCacheKye(){
        return "";
    }
}
