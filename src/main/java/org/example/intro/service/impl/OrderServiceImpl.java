package org.example.intro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.intro.dto.order.CreateOrderDto;
import org.example.intro.dto.order.OrderDto;
import org.example.intro.mapper.OrderMapper;
import org.example.intro.model.Order;
import org.example.intro.model.User;
import org.example.intro.repository.order.OrderRepository;
import org.example.intro.sequrity.CustomUserDetailsService;
import org.example.intro.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public OrderDto placeOrder(
            CreateOrderDto requestDto, Authentication authentication
    ) {
//        Order order = orderMapper.toEntity(requestDto);
//        Long userId = ((User) userDetailsService
//                .loadUserByUsername(authentication.getName())).getId();
//        order.setUser(new User(userId));
//        orderRepository.save(order);
//        System.out.println(order);
//        return orderMapper.toDto(order);
        return null;
    }
}
