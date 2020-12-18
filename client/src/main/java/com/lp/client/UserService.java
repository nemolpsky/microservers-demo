package com.lp.client;

import org.springframework.web.bind.annotation.RequestMapping;

public interface UserService {

    @RequestMapping(value = "/find")
    User find();

}
