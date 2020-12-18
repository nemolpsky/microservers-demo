package com.lp.client;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Fallback implements FallbackFactory<UserClient> {

    private Logger logger = LoggerFactory.getLogger(Fallback.class);

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public User find() {
                return null;
            }

            @Override
            public User get() {
                logger.error("fallback, reason is ",throwable);
                return new User(-1);
            }

            @Override
            public User findByAge(Param param) {
                return null;
            }

        };
    }
}
