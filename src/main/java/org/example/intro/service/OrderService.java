package org.example.intro.service;

import java.util.List;
import org.example.intro.dto.order.CreateOrderDto;
import org.example.intro.dto.order.OrderDto;
import org.example.intro.dto.order.OrderItemDto;
import org.example.intro.dto.order.UpdateOrderStatusDto;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto placeOrder(CreateOrderDto orderDto, Long userId);

    List<OrderDto> getOrders(Pageable pageable, Long userId);

    List<OrderItemDto> getOrderItems(Long orderId, Long userId);

    OrderItemDto getOrderItem(
            Long orderId, Long itemId, Long userI
    );

    OrderDto updateStatus(Long orderId, UpdateOrderStatusDto requestDto);
}
