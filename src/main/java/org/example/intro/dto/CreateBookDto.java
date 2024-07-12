package org.example.intro.dto;

import java.math.BigDecimal;

public record CreateBookDto(
        String title,
        String author,
        BigDecimal price,
        String description,
        String isbn,
        String coverImage
) {
}
