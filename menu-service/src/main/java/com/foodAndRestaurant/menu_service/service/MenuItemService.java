package com.foodAndRestaurant.menu_service.service;

import com.foodAndRestaurant.menu_service.dto.MenuItemRequest;
import com.foodAndRestaurant.menu_service.dto.MenuItemResponse;
import com.foodAndRestaurant.menu_service.entity.MenuItem;
import com.foodAndRestaurant.menu_service.entity.RestaurantProfile;
import com.foodAndRestaurant.menu_service.repository.MenuItemRepository;
import com.foodAndRestaurant.menu_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemResponse addItem(Long userId, MenuItemRequest menuItemRequest) {
        RestaurantProfile restaurantProfile = restaurantRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Restaurant profile not found"));

        MenuItem menuItem = MenuItem.builder()
                .price(menuItemRequest.getPrice())
                .name(menuItemRequest.getName())
                .category(menuItemRequest.getCategory())
                .description(menuItemRequest.getDescription())
                .restaurantId(restaurantProfile.getId())
                .available(true)
                .build();

        menuItemRepository.save(menuItem);
        return toResponse(menuItem);
    }

    public List<MenuItemResponse> getMyMenuItems(Long userId) {

        RestaurantProfile restaurantProfile = restaurantRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Restaurant profile not found"));

        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantProfile.getId());

        return menuItems.stream().map(this::toResponse).toList();
    }

    public List<MenuItemResponse> getItemsByRestaurant(Long restaurantId) {
        RestaurantProfile restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (!restaurant.isApproved()) {
            throw new RuntimeException("Restaurant is not approved yet");
        }

        return menuItemRepository.findByRestaurantIdAndAvailableTrue(restaurantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MenuItemResponse updateItem(Long userId, Long itemId, MenuItemRequest request) {

        RestaurantProfile profile = restaurantRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Restaurant profile not found"));

        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(()-> new RuntimeException("Menu item not found"));

        if(!item.getRestaurantId().equals(profile.getId())) {
            throw new RuntimeException("You don't own this menu item");
        }

        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());

        menuItemRepository.save(item);
        return toResponse(item);
    }

    public void deleteItem(Long userId, Long itemId) {
        RestaurantProfile restaurant = restaurantRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Restaurant profile not found"));

        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (!item.getRestaurantId().equals(restaurant.getId())) {
            throw new RuntimeException("You don't own this menu item");
        }
        menuItemRepository.delete(item);
    }

    private MenuItemResponse toResponse(MenuItem item) {
        return MenuItemResponse.builder()
                .id(item.getId())
                .restaurantId(item.getRestaurantId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .category(item.getCategory())
                .available(item.isAvailable())
                .build();
    }
}
