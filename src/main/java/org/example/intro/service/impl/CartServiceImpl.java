package org.example.intro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.intro.dto.cart.ShoppingCartDto;
import org.example.intro.mapper.CartItemMapper;
import org.example.intro.mapper.CartMapper;
import org.example.intro.model.ShoppingCart;
import org.example.intro.model.User;
import org.example.intro.repository.cart.CartRepository;
import org.example.intro.repository.user.UserRepository;
import org.example.intro.sequrity.CustomUserDetailsService;
import org.example.intro.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CustomUserDetailsService userDetailsService;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getCart(Authentication authentication) {
        Long cartId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName()))
                .getShoppingCart().getId();
        Optional<ShoppingCart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            cart.get().getCartItems().stream()
                    .map(cartItemMapper::toCartItemDto).forEach(cartItem -> {
                System.out.println(cartItem.getBookTitle());
            });
        }
        return cartMapper.toCartDto(cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found")));
    }
}
