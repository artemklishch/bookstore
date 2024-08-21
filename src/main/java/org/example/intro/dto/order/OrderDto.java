package org.example.intro.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.example.intro.enums.OrderStatus;

public record OrderDto(
        Long id,
        Long userId,
        List<OrderItemDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        OrderStatus status
) {
}
