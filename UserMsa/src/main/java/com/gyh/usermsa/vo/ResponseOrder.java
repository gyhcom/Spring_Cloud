package com.gyh.usermsa.vo;

import java.util.Date;
import lombok.Getter;

@Getter
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
}
