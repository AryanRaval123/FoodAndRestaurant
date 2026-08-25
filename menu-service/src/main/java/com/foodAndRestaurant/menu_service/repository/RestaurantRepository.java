package com.foodAndRestaurant.menu_service.repository;

import com.foodAndRestaurant.menu_service.entity.RestaurantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantProfile, Long> {
    Optional<RestaurantProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    List<RestaurantProfile> findByApprovedTrue();
}