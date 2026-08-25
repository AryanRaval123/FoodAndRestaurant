package com.foodAndRestaurant.menu_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestaurantRequest {

    @NotBlank(message = "Restaurant name is required")
    private String restaurantName;

    @NotBlank(message = "Address is required")
    private String address;

    private String cuisineType;
}