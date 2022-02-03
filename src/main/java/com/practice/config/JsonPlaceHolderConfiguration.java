package com.practice.config;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class JsonPlaceHolderConfiguration {

    @Bean
    public OkHttpClient client(){
        return new OkHttpClient();
    }
}
