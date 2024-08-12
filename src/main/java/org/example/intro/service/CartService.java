package org.example.intro.service;

import org.example.intro.dto.cart.ShoppingCartDto;
import org.springframework.security.core.Authentication;

public interface CartService {
    ShoppingCartDto getCart(Authentication authentication);
}
