package com.gyh.usermsa.controller;

import com.gyh.usermsa.vo.Greeting;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private Greeting greeting;

    @GetMapping("/heath_check")
    public String status() {
        return "UserService Working";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();
    }
}
