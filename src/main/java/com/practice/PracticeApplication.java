package com.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }

    //ModelMapper modelMapper = new ModelMapper();
    // user here is a prepopulated User instance
    //UserDTO userDTO = modelMapper.map(user, UserDTO.class);
}
