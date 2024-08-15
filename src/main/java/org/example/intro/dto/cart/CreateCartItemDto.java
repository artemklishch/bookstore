package org.example.intro.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemDto {
    @NotNull(message = "The book identifier is mandatory")
    private Long bookId;
    @Positive(message = "The value should be positive")
    @Max(value = 10000, message = "Max value shouldn't be over 1000")
    private int quantity;
}
