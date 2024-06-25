package com.gyh.ordersmsa.jpa.repository;

import com.gyh.ordersmsa.jpa.OrdersEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<OrdersEntity, Long> {

    OrdersEntity findByOrderId(String orderId);
    Iterable<OrdersEntity> findByUserId(String userId);

}
