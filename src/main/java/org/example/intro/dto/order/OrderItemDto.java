package org.example.intro.dto.order;

public record OrderItemDto(
        Long id,
        Long bookId,
        int quantity
) {
}
