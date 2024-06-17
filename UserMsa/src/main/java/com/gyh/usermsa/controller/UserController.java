package com.gyh.usermsa.controller;

import com.gyh.usermsa.dto.UserDto;
import com.gyh.usermsa.service.UserService;
import com.gyh.usermsa.vo.Greeting;
import com.gyh.usermsa.vo.RequestUser;
import com.gyh.usermsa.vo.ResponseUser;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    public UserController(UserService userService, Greeting greeting) {
        this.userService = userService;
        this.greeting = greeting;
    }

    private UserService userService;
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
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
