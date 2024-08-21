package org.example.intro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.order.CreateOrderDto;
import org.example.intro.dto.order.OrderDto;
import org.example.intro.dto.order.OrderItemDto;
import org.example.intro.dto.order.UpdateOrderStatusDto;
import org.example.intro.model.User;
import org.example.intro.sequrity.CustomUserDetailsService;
import org.example.intro.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders management", description = "Endpoints for orders management")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CustomUserDetailsService userDetailsService;
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place the order", description = "Place the order")
    public OrderDto placeOrder(
            @RequestBody @Valid CreateOrderDto requestDto,
            Authentication authentication
    ) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        return orderService.placeOrder(requestDto, userId);
    }

    @Operation(summary = "Get orders", description = "Get orders")
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public List<OrderDto> getOrders(
            Authentication authentication, Pageable pageable
    ) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        return orderService.getOrders(pageable, userId);
    }

    @Operation(summary = "Get the certain order", description = "Get the certain order by ID")
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getOrderItems(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        return orderService.getOrderItems(orderId, userId);
    }

    @Operation(summary = "Get the certain order item", description = "Get the certain order item by IDs")
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{orderId}/items/{id}")
    public OrderItemDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        return orderService.getOrderItem(orderId, id, userId);
    }

    @Operation(summary = "Update order status", description = "Update order status")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{id}")
    public OrderDto updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusDto requestDto
            ){
        return orderService.updateStatus(id, requestDto);
    }
}
