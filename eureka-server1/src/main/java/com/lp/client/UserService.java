package com.lp.client;

import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.web.bind.annotation.RequestMapping;

public interface UserService {

    @CacheResult
    @RequestMapping(value = "/find")
    User find();

}
