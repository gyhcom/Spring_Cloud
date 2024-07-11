package com.gyh.usermsa.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Greeting {
    @Value("${greeting.message}")
    private String message;

    @Value("${gateway.ip}")
    private String ip;

    @Value("${token.secret}")
    private String secret;
}