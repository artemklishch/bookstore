package org.example.intro.dto.book;

import java.math.BigDecimal;

public record BookDtoWithoutCategoryIds(
        Long id,
        String title,
        String author,
        BigDecimal price,
        String description,
        String coverImage
) {
}
