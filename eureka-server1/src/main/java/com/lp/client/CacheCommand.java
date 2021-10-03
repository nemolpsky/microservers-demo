package com.lp.client;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

public class CacheCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;
    private Object param;

    public CacheCommand(Setter setter, RestTemplate restTemplate, Object param) {
        super(setter);
        this.restTemplate = restTemplate;
        this.param = param;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://localhost:10002/testGet",String.class);
    }

    @Override
    protected String getCacheKey() {
        return "1";
    }

    public void flushRequestCache(){
        HystrixRequestCache.getInstance(
                HystrixCommandKey.Factory.asKey("key3"), HystrixConcurrencyStrategyDefault.getInstance())
                .clear("1");
    }
}
