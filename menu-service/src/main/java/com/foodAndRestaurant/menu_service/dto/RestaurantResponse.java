package com.foodAndRestaurant.menu_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private Long id;
    private Long userId;
    private String restaurantName;
    private String address;
    private String cuisineType;
    private boolean approved;
}