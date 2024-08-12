package org.example.intro.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingCartDto{
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
