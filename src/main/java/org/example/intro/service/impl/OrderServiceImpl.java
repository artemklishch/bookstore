package org.example.intro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.order.CreateOrderDto;
import org.example.intro.dto.order.OrderDto;
import org.example.intro.dto.order.OrderItemDto;
import org.example.intro.dto.order.UpdateOrderStatusDto;
import org.example.intro.enums.OrderStatus;
import org.example.intro.mapper.OrderItemMapper;
import org.example.intro.mapper.OrderMapper;
import org.example.intro.model.Order;
import org.example.intro.model.User;
import org.example.intro.repository.order.OrderItemRepository;
import org.example.intro.repository.order.OrderRepository;
import org.example.intro.sequrity.CustomUserDetailsService;
import org.example.intro.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomUserDetailsService userDetailsService;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDto placeOrder(CreateOrderDto requestDto, Long userId) {
        Order order = orderMapper.toEntity(requestDto);
        order.setUser(new User(userId));
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getOrders(Pageable pageable, Long userId) {
        return orderMapper.toOrdersDto(
                orderRepository.findByUserId(userId, pageable)
        );
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId, Long userId) {
        return orderItemMapper.toOrderItemsDto(
                orderRepository.findOrderItems(orderId, userId)
        );
    }

    @Override
    public OrderItemDto getOrderItem(
            Long orderId, Long itemId, Long userId
    ) {
        getOrder(orderId, userId);
        return Optional.ofNullable(orderItemMapper.toDto(
                orderItemRepository.getOrderItem(orderId, itemId)
        )).orElseThrow(() -> new EntityNotFoundException(
                "Not fount the item with the id: " + itemId)
        );
    }

    @Override
    public OrderDto updateStatus(Long orderId, UpdateOrderStatusDto requestDto) {
        Order order = getOrder(orderId, null);
        order.setStatus(OrderStatus.valueOf(requestDto.getStatus()));
        return orderMapper.toDto(orderRepository.save(order));
    }

    private Order getOrder(Long orderId, Long userId) {
        Optional<Order> order;
        if (userId == null) {
            order = orderRepository.findOrder(orderId);
        } else {
            order = orderRepository.findOrder(orderId, userId);
        }
        return order.orElseThrow(
                () -> new EntityNotFoundException(
                        "Not found the order with the id: " + orderId
                )
        );
    }
}
