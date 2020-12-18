package com.lp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Fallback2 implements UserClient {

    private Logger logger = LoggerFactory.getLogger(Fallback2.class);

    @Override
    public User get() {
        return null;
    }

    @Override
    public User findByAge(Param param) {
        return null;
    }

    @Override
    public User find() {
        return null;
    }
}
