package com.foodAndRestaurant.order_service.clients;

import com.foodAndRestaurant.order_service.dtos.PaymentRequest;
import com.foodAndRestaurant.order_service.dtos.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/api/payments")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);
}