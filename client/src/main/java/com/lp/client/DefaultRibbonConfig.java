package com.lp.client;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultRibbonConfig {
    @Bean
    public IRule ribbonRule() {
        return new CustomRule();
    }

}
