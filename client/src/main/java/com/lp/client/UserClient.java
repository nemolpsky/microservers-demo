package com.lp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user",path = "/user/server", fallbackFactory  = Fallback.class,configuration = FeignLogConfig.class)
public interface UserClient extends UserService{

    @RequestMapping(value = "/get")
    User get();

    @RequestMapping(value = "/findByAge")
    User findByAge(@SpringQueryMap Param param);
}
