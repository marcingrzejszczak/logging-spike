package com.spike.server;

import com.spike.TraceContextInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new TraceContextInterceptor());
//    }

    @Bean
    public RestTemplate createRestTemplate() {
//        return new RestTemplateBuilder()
//                .interceptors(new RestTemplateTraceContextInterceptor(),
//                        new ResponseTrendLoggingInterceptor())
//                .build();
        return new RestTemplateBuilder()
                .interceptors(new ResponseTrendLoggingInterceptor())
                .build();
    }
}
