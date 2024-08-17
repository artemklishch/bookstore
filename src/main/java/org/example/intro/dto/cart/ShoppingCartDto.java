package org.example.intro.dto.cart;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDto{
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
