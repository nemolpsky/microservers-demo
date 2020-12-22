package com.lp.client;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClientApplicationTests {

    @Test
    void testController() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:10002/hystrixGet1?time=0";
        ResponseEntity<String> response1 = restTemplate.getForEntity(url, String.class);
        ResponseEntity<String> response2 = restTemplate.getForEntity(url, String.class);
        ResponseEntity<String> response3 = restTemplate.getForEntity(url, String.class);
    }

}
