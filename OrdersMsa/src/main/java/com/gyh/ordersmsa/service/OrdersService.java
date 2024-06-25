package com.gyh.ordersmsa.service;

import com.gyh.ordersmsa.dto.OrdersDto;
import com.gyh.ordersmsa.jpa.OrdersEntity;

public interface OrdersService {

    OrdersDto createOrder(OrdersDto orderDetails);

    OrdersDto getOrderByOrderId(String orderId);

    Iterable<OrdersEntity> getOrderByUserId(String userId);
}
