package com.foodAndRestaurant.order_service.service;

import com.foodAndRestaurant.order_service.clients.MenuServiceClient;
import com.foodAndRestaurant.order_service.clients.PaymentServiceClient;
import com.foodAndRestaurant.order_service.dtos.*;
import com.foodAndRestaurant.order_service.entity.Order;
import com.foodAndRestaurant.order_service.entity.OrderItem;
import com.foodAndRestaurant.order_service.entity.OrderStatus;
import com.foodAndRestaurant.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuServiceClient menuServiceClient;
    private final PaymentServiceClient paymentServiceClient;


    public OrderResponse placeOrder(Long customerId, OrderRequest request) {

        List<MenuItemDto> restaurantItems = menuServiceClient.getItemsByRestaurant(request.getRestaurantId());

        Map<Long, MenuItemDto> itemMap = restaurantItems.stream()
                .collect(Collectors.toMap(MenuItemDto::getId, item -> item));


        Order order = Order.builder()
                .restaurantId(request.getRestaurantId())
                .status(OrderStatus.PLACED)
                .customerId(customerId)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {

            MenuItemDto menuItem = itemMap.get(itemReq.getMenuItemId());

            if (menuItem == null) {
                throw new RuntimeException("Menu item not found: " + itemReq.getMenuItemId());
            }
            if (!menuItem.isAvailable()) {
                throw new RuntimeException("Item currently unavailable: " + menuItem.getName());
            }

            BigDecimal lineTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(lineTotal);

            OrderItem orderItem = OrderItem.builder()
                    .menuItemId(menuItem.getId())
                    .itemName(menuItem.getName())
                    .price(menuItem.getPrice())
                    .quantity(itemReq.getQuantity())
                    .build();

            order.addItem(orderItem);
        }

        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        // 3. Attempt payment
        PaymentResponse paymentResponse = paymentServiceClient.processPayment(
                PaymentRequest.builder()
                        .orderId(savedOrder.getId())
                        .customerId(customerId)
                        .amount(total)
                        .build()
        );

        // 4. Update status based on payment result
        if ("SUCCESS".equals(paymentResponse.getStatus())) {
            savedOrder.setStatus(OrderStatus.CONFIRMED);
        } else {
            savedOrder.setStatus(OrderStatus.CANCELLED);
        }

        orderRepository.save(savedOrder);
        return toResponse(savedOrder);
    }

    public List<OrderResponse> getMyOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<OrderResponse> getRestaurantOrders(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        orderRepository.save(order);
        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(i -> OrderItemResponse.builder()
                        .menuItemId(i.getMenuItemId())
                        .itemName(i.getItemName())
                        .price(i.getPrice())
                        .quantity(i.getQuantity())
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .restaurantId(order.getRestaurantId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(itemResponses)
                .createdAt(order.getCreatedAt())
                .build();
    }
}
