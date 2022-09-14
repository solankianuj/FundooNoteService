package com.example.fundoonotemicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * purpose-creating rest template bean.
 */

@Component
public class NoteConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
