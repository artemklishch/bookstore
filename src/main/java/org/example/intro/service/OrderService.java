package org.example.intro.service;

import java.util.List;
import org.example.intro.dto.order.CreateOrderDto;
import org.example.intro.dto.order.OrderDto;
import org.example.intro.dto.order.OrderItemDto;
import org.example.intro.dto.order.UpdateOrderStatusDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderDto placeOrder(CreateOrderDto orderDto, Authentication authentication);

    List<OrderDto> getOrders(Authentication authentication, Pageable pageable);

    List<OrderItemDto> getOrderItems(Long orderId, Authentication authentication);

    OrderItemDto getOrderItem(
            Long orderId, Long itemId, Authentication authentication
    );

    OrderDto updateStatus(Long orderId, UpdateOrderStatusDto requestDto);
}
