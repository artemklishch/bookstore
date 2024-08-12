package org.example.intro.dto.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class UpdateCartItemsQuantityDto {
    @NotBlank(message = "Quantity is mandatory field")
    @Positive(message = "Quantity value has to be positive")
    int quantity;
}
