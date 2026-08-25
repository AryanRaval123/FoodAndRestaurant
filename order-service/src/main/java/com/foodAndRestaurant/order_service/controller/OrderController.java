package com.foodAndRestaurant.order_service.controller;

import com.foodAndRestaurant.order_service.dtos.OrderRequest;
import com.foodAndRestaurant.order_service.dtos.OrderResponse;
import com.foodAndRestaurant.order_service.dtos.OrderStatusUpdateRequest;
import com.foodAndRestaurant.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @Valid @RequestBody OrderRequest request) {

        if (!"CUSTOMER".equals(role)) {
            throw new RuntimeException("Only customers can place orders");
        }
        return ResponseEntity.ok(orderService.placeOrder(userId, request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(orderService.getMyOrders(userId));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getRestaurantOrders(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long restaurantId) {

        if (!"RESTAURANT_OWNER".equals(role)) {
            throw new RuntimeException("Only restaurant owners can view restaurant orders");
        }
        return ResponseEntity.ok(orderService.getRestaurantOrders(restaurantId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequest request) {

        if (!"RESTAURANT_OWNER".equals(role)) {
            throw new RuntimeException("Only restaurant owners can update order status");
        }
        return ResponseEntity.ok(orderService.updateStatus(orderId, request.getStatus()));
    }
}