package com.gyh.ordersmsa.service;

import com.gyh.ordersmsa.dto.OrdersDto;
import com.gyh.ordersmsa.jpa.OrdersEntity;
import com.gyh.ordersmsa.jpa.repository.OrdersRepository;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService{

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    private OrdersRepository ordersRepository;

    @Override
    public OrdersDto createOrder(OrdersDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrdersEntity ordersEntity = modelMapper.map(orderDetails, OrdersEntity.class);

        ordersRepository.save(ordersEntity);

        OrdersDto returnValue = modelMapper.map(ordersEntity, OrdersDto.class);
        return returnValue;

    }

    @Override
    public OrdersDto getOrderByOrderId(String orderId) {
        OrdersEntity ordersEntity = ordersRepository.findByOrderId(orderId);
        OrdersDto ordersDto = new ModelMapper().map(ordersEntity, OrdersDto.class);

        return ordersDto;
    }

    @Override
    public Iterable<OrdersEntity> getOrderByUserId(String userId) {
        return ordersRepository.findByUserId(userId);
    }
}
