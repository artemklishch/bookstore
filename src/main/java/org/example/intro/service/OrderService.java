package org.example.intro.service;

import org.example.intro.dto.order.CreateOrderDto;
import org.example.intro.dto.order.OrderDto;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderDto placeOrder(CreateOrderDto orderDto, Authentication authentication);
}
