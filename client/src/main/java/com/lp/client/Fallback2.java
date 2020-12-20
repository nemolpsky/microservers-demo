package com.lp.client;

import org.springframework.stereotype.Component;

@Component
public class Fallback2 implements SystemClient {

    @Override
    public User get() {
        return null;
    }
}
