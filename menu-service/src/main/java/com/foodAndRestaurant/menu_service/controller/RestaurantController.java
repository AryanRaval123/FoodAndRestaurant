package com.foodAndRestaurant.menu_service.controller;

import com.foodAndRestaurant.menu_service.dto.RestaurantRequest;
import com.foodAndRestaurant.menu_service.dto.RestaurantResponse;
import com.foodAndRestaurant.menu_service.service.RestaurantService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestHeader("X-User-Id") Long userId,
                                                               @RequestHeader("X-User-Role") String role,
                                                              @Valid @RequestBody RestaurantRequest request) {

        if (!"RESTAURANT_OWNER".equals(role)) {
            throw new RuntimeException("role isn't supported to create restaurant profile");
        }
        return ResponseEntity.ok(restaurantService.createRestaurant(userId, request));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<RestaurantResponse> approveRestaurant(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id) {

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Only admins can approve restaurants");
        }
        return ResponseEntity.ok(restaurantService.approveRestaurant(id));
    }

    @GetMapping("/me")
    public ResponseEntity<RestaurantResponse> getMyRestaurant(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(restaurantService.getMyRestaurant(userId));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllApprovedRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllApprovedRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }
}
