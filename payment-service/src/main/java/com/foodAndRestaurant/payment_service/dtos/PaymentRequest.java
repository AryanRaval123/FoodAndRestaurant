package com.foodAndRestaurant.payment_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
