package com.gyh.ordersmsa.controller;


import com.gyh.ordersmsa.dto.OrdersDto;
import com.gyh.ordersmsa.jpa.OrdersEntity;
import com.gyh.ordersmsa.service.OrdersService;
import com.gyh.ordersmsa.vo.RequestOrder;
import com.gyh.ordersmsa.vo.ResponseOrders;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
public class OrdersController {

    Environment env;

    private OrdersService ordersService;

    public OrdersController(Environment env, OrdersService ordersService) {
        this.env = env;
        this.ordersService = ordersService;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Orders Service On port %s",
            env.getProperty("local.server.port"));
    }

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<ResponseOrders> createOrder(@PathVariable("userId") String userId,
        @RequestBody RequestOrder orderDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrdersDto ordersDto = modelMapper.map(orderDetails, OrdersDto.class);
        ordersDto.setUserId(userId);
        OrdersDto createDto = ordersService.createOrder(ordersDto);

        ResponseOrders returnValue = modelMapper.map(createDto, ResponseOrders.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{userId}/orders")
    public ResponseEntity<List<ResponseOrders>> getOrder(@PathVariable("userId") String userId){
        Iterable<OrdersEntity> orderList = ordersService.getOrderByUserId(userId);
        List<ResponseOrders> result = new ArrayList<>();
        orderList.forEach(a -> {
            result.add(new ModelMapper().map(a, ResponseOrders.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}
