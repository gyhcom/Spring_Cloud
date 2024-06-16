package com.gyh.usermsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserMsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMsaApplication.class, args);
    }

}
