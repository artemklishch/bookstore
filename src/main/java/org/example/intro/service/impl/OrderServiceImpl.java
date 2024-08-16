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
import org.springframework.security.core.Authentication;
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
    public OrderDto placeOrder(
            CreateOrderDto requestDto, Authentication authentication
    ) {
        Order order = orderMapper.toEntity(requestDto);
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        order.setUser(new User(userId));
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getOrders(
            Authentication authentication, Pageable pageable
    ) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        return orderRepository.findByUserId(userId, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemDto> getOrderItems(
            Long orderId, Authentication authentication
    ) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        return orderRepository.findOrderItems(orderId, userId)
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItem(
            Long orderId, Long itemId, Authentication authentication
    ) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        orderRepository.findOrder(orderId, userId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Not found the order with the id: " + orderId
                        )
                );
        return Optional.ofNullable(orderItemMapper.toDto(
                orderItemRepository.getOrderItem(orderId, itemId)
        )).orElseThrow(() -> new EntityNotFoundException(
                "Not fount the item with the id: " + itemId)
        );
    }

    @Override
    public OrderDto updateStatus(Long orderId, UpdateOrderStatusDto requestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Not found the order with the id: " + orderId
                )
        );
        order.setStatus(OrderStatus.valueOf(requestDto.getStatus()));
        return orderMapper.toDto(orderRepository.save(order));
    }
}
