package com.lp.client;

import org.springframework.web.bind.annotation.RequestMapping;

//@Service
public interface UserService {

    @RequestMapping(value = "/find")
    User find();

}
