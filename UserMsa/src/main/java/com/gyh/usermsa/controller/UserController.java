package com.gyh.usermsa.controller;

import com.gyh.usermsa.dto.UserDto;
import com.gyh.usermsa.jpa.UserEntity;
import com.gyh.usermsa.service.UserService;
import com.gyh.usermsa.vo.Greeting;
import com.gyh.usermsa.vo.RequestUser;
import com.gyh.usermsa.vo.ResponseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * *@RequestMapping("/")
 * 게이트웨이를 통해 접속하게 되면 게이트웨이에 yml에 등록된
 * cloud gateway route id 를 메소드 호출할때 등록해줘야 한다.
 * 그래야 정확하게 메소드가 매핑되어 들어온다.
 * GetMapping("/hello") -> GetMapping("/등록된 route id/hello")
 * 아래와 같이 해도 되고 메소드에 일일이 해도 되고
*/
@RestController

@RequestMapping("/")
public class UserController {
    private UserService userService;
    private Greeting greeting;
    private Environment env;

    public UserController(UserService userService, Greeting greeting, Environment env) {
        this.userService = userService;
        this.greeting = greeting;
        this.env = env;
    }


    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service"
            + ", port(local.server.port)=" + env.getProperty("local.server.port")
            + ", port(server.port)=" + env.getProperty("server.port")
            + ", gateway ip(env)=" + env.getProperty("gateway.ip")
//            + ", gateway ip(value)=" + greeting.getIp()
            + ", message=" + env.getProperty("greeting.message")
            + ", tokenSecret=" + env.getProperty("token.secret")
//            + ", token secret=" + greeting.getSecret()
            + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, HttpServletResponse response){
        System.out.println("users.welcome ip:" + request.getRemoteAddr() +
            "," + request.getRemoteHost() +
            "," + request.getRequestURI() +
            "," + request.getRequestURL());

    return greeting.getMessage();
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();

        //람다
        userList.forEach(a ->{
            result.add(new ModelMapper().map(a, ResponseUser.class));
        });

        /*for문
        for (UserEntity a : userList) {
            System.out.println(a);
            result.add(new ModelMapper().map(a, ResponseUser.class));
        }*/

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser result = new ModelMapper().map(userDto, ResponseUser.class);

        EntityModel entityModel = EntityModel.of(result);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).getUsers());
        entityModel.add(linkTo.withRel("all-users"));

        try {
            return ResponseEntity.status(HttpStatus.OK).body(entityModel);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
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
