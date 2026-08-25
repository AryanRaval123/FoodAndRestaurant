package com.foodAndRestaurant.menu_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String address;

    private String cuisineType;

    @Column(nullable = false)
    private boolean approved = false;
}