package com.practice.config;

import com.practice.exception.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class JsonPlaceHolderConfiguration {

    @Bean
    public OkHttpClient client(){
        return new OkHttpClient();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
