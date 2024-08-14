package org.example.intro.service;

import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.dto.cart.ShoppingCartDto;
import org.example.intro.dto.cart.UpdateCartItemsQuantityDto;
import org.example.intro.model.User;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    ShoppingCartDto getCart(Authentication authentication);

    ShoppingCartDto createCartItem(
            CreateCartItemDto createCartItemDto,
            Authentication authentication
    );

    ShoppingCartDto updateCartItem(
            Long cartItemId,
            UpdateCartItemsQuantityDto requestDto,
            Authentication authentication
    );

    void deleteCartItem(Long cartItemId, Authentication authentication);

    void createShoppingCart(User user);
}
