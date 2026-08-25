package com.foodAndRestaurant.menu_service.controller;

import com.foodAndRestaurant.menu_service.dto.MenuItemRequest;
import com.foodAndRestaurant.menu_service.dto.MenuItemResponse;
import com.foodAndRestaurant.menu_service.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponse> addItem(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @Valid @RequestBody MenuItemRequest request) {

        if (!"RESTAURANT_OWNER".equals(role)) {
            throw new RuntimeException("Only restaurant owners can add menu items");
        }

        return ResponseEntity.ok(menuItemService.addItem(userId, request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<MenuItemResponse>> getMyItems(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(menuItemService.getMyMenuItems(userId));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemResponse>> getItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getItemsByRestaurant(restaurantId));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<MenuItemResponse> updateItem(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long itemId,
            @Valid @RequestBody MenuItemRequest request) {
        if (!"RESTAURANT_OWNER".equals(role)) {
            throw new RuntimeException("Only restaurant owners can add menu items");
        }
        return ResponseEntity.ok(menuItemService.updateItem(userId, itemId, request));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long itemId) {

        if (!"RESTAURANT_OWNER".equals(role)) {
            throw new RuntimeException("Only restaurant owners can add menu items");
        }
        menuItemService.deleteItem(userId, itemId);
        return ResponseEntity.noContent().build();
    }
}