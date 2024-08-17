package org.example.intro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.cart.CartItemDto;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.dto.cart.ShoppingCartDto;
import org.example.intro.dto.cart.UpdateCartItemsQuantityDto;
import org.example.intro.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Cart item management", description = "Endpoints for cart items management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final ShoppingCartService cartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(
            summary = "Retrieve the shopping cart",
            description = "Retrieve the shopping cart data with cart items"
    )
    public ShoppingCartDto getCart(Authentication authentication){
        return cartService.getCart(authentication);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add cart item to the shopping cart",
            description = "Add cart item to the shopping cart"
    )
    public ShoppingCartDto addToCart(
            Authentication authentication,
            @RequestBody @Valid CreateCartItemDto requestDto
    ){
        return cartService.createCartItem(requestDto, authentication);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update cart item quantity",
            description = "Update cart item quantity in the shopping cart"
    )
    public ShoppingCartDto updateCartItemsQuantity(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemsQuantityDto quantityDto,
            Authentication authentication
    ){
        return cartService.updateCartItem(cartItemId, quantityDto, authentication);
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(
            summary = "Delete cart item",
            description = "Delete cart item in the shopping cart"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ShoppingCartDto deleteCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId
    ){
        return cartService.deleteCartItem(cartItemId, authentication);
    }
}
