package com.foodAndRestaurant.order_service.dtos;

import com.foodAndRestaurant.order_service.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    @NotNull(message = "Status is required")
    private OrderStatus status;
}