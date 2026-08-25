package com.foodAndRestaurant.order_service.clients;

import com.foodAndRestaurant.order_service.dtos.MenuItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "menu-service")
public interface  MenuServiceClient {
    @GetMapping("/api/menu-items/restaurant/{restaurantId}")
    List<MenuItemDto> getItemsByRestaurant(@PathVariable("restaurantId") Long restaurantId);
}