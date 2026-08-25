package com.foodAndRestaurant.order_service.repository;

import com.foodAndRestaurant.order_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}