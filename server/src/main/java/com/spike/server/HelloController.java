package com.spike.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    private RestTemplate restTemplate;

    private static Logger log = LoggerFactory.getLogger(HelloController.class);

    public HelloController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/greet")
    public Hello greet() {
        log.info("Received greet request");
        restTemplate.postForLocation("http://localhost:8081/audit", HttpMethod.POST, new HttpEntity<>("Foo Bar"));
        return new Hello("hello world");
    }
}

