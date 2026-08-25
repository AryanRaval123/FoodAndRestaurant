package com.foodAndRestaurant.menu_service.service;

import com.foodAndRestaurant.menu_service.dto.RestaurantRequest;
import com.foodAndRestaurant.menu_service.dto.RestaurantResponse;
import com.foodAndRestaurant.menu_service.entity.RestaurantProfile;
import com.foodAndRestaurant.menu_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantResponse createRestaurant(Long userId, RestaurantRequest request) {
        if (restaurantRepository.existsByUserId(userId)) {
            throw new RuntimeException("Restaurant profile already exists for this user");
        }

        RestaurantProfile profile = RestaurantProfile.builder()
                .userId(userId)
                .restaurantName(request.getRestaurantName())
                .address(request.getAddress())
                .cuisineType(request.getCuisineType())
                .approved(false)
                .build();

        restaurantRepository.save(profile);
        return toResponse(profile);
    }

    public RestaurantResponse getMyRestaurant(Long userId) {
        RestaurantProfile profile = restaurantRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Restaurant profile not found"));
        return toResponse(profile);
    }

    public RestaurantResponse getRestaurantById(Long id) {
        RestaurantProfile profile = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return toResponse(profile);
    }

    public RestaurantResponse approveRestaurant(Long restaurantId) {
        RestaurantProfile restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setApproved(true);
        restaurantRepository.save(restaurant);

        return toResponse(restaurant);
    }

    public List<RestaurantResponse> getAllApprovedRestaurants() {
        return restaurantRepository.findByApprovedTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private RestaurantResponse toResponse(RestaurantProfile profile) {
        return RestaurantResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .restaurantName(profile.getRestaurantName())
                .address(profile.getAddress())
                .cuisineType(profile.getCuisineType())
                .approved(profile.isApproved())
                .build();
    }
}