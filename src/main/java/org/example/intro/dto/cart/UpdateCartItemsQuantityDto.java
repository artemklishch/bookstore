package org.example.intro.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemsQuantityDto {
    @Positive(message = "Quantity value has to be positive")
    int quantity;
}
