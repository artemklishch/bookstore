package org.example.intro.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemsQuantityDto {
    @Positive(message = "Quantity value has to be positive")
    int quantity;
}
