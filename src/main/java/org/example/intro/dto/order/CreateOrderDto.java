package org.example.intro.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderDto {
    @NotNull(message = "Shipping address is mandatory")
    private String shippingAddress;
}
