package org.example.intro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookDto {
    @NotNull
    String title;

    @NotNull
    String author;

    @NotNull
    @Min(0)
    BigDecimal price;

    String description;

    @NotNull
    String isbn;

    String coverImage;
}
