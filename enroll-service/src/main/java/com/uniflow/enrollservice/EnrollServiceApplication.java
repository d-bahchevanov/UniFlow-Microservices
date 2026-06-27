package com.uniflow.enrollservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EnrollServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnrollServiceApplication.class, args);
    }

}
