package org.example.intro.dto.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateCartItemDto {
    @NotBlank(message = "The book identifier is mandatory")
    private Long bookId;
    @Size(max = 1000, message = "Impossible to add over 1000 items per one time")
    @Positive(message = "The value should be positive")
    private int quantity;
}
