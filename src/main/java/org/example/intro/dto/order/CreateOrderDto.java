package org.example.intro.dto.order;

import jakarta.validation.constraints.NotNull;

public class CreateOrderDto {
    @NotNull(message = "Shipping address is mandatory")
    private String shippingAddress;
}
