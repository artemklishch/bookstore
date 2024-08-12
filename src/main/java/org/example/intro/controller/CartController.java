package org.example.intro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.dto.cart.ShoppingCartDto;
import org.example.intro.dto.cart.UpdateCartItemsQuantityDto;
import org.example.intro.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Cart item management", description = "Endpoints for cart items management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Retrieve the shopping cart", description = "Retrieve the shopping cart data with cart items")
    public ShoppingCartDto getCart(Authentication authentication){
        return cartService.getCart(authentication);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Add cart item to the shopping cart", description = "Add cart item to the shopping cart")
    public boolean addToCart(CreateCartItemDto requestDto){
        return false;
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Update cart item quantity", description = "Update cart item quantity in the shopping cart")
    public boolean updateCartItemsQuantity(
            @PathVariable Long cartItemId,
            UpdateCartItemsQuantityDto quantityDto
    ){
        return false;
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Delete cart item", description = "Delete cart item in the shopping cart")
    public boolean deleteCartItem(@PathVariable Long cartItemId){
        return false;
    }
}
