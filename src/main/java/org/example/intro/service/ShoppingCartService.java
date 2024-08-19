package org.example.intro.service;

import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.dto.cart.ShoppingCartDto;
import org.example.intro.dto.cart.UpdateCartItemsQuantityDto;
import org.example.intro.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getCart(Long userId);

    ShoppingCartDto createCartItem(
            CreateCartItemDto createCartItemDto,
            Long userId
    );

    ShoppingCartDto updateCartItem(
            Long cartItemId,
            UpdateCartItemsQuantityDto requestDto,
            Long userId
    );

    ShoppingCartDto deleteCartItem(Long cartItemId, Long userId);

    void createShoppingCart(User user);
}
