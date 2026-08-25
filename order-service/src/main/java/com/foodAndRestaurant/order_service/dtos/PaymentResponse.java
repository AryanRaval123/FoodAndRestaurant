package com.foodAndRestaurant.order_service.dtos;

import lombok.Data;

@Data
public class PaymentResponse {
    private Long paymentId;
    private String status; // SUCCESS / FAILED
    private String transactionId;
}
