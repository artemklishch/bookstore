package org.example.intro.service.impl;

import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.dto.cart.ShoppingCartDto;
import org.example.intro.dto.cart.UpdateCartItemsQuantityDto;
import org.example.intro.exceptions.ProceedingException;
import org.example.intro.mapper.CartItemMapper;
import org.example.intro.mapper.CartMapper;
import org.example.intro.model.Book;
import org.example.intro.model.CartItem;
import org.example.intro.model.ShoppingCart;
import org.example.intro.model.User;
import org.example.intro.repository.book.BookRepository;
import org.example.intro.repository.cart.CartItemRepository;
import org.example.intro.repository.cart.CartRepository;
import org.example.intro.sequrity.CustomUserDetailsService;
import org.example.intro.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CustomUserDetailsService userDetailsService;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getCart(Authentication authentication) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        ShoppingCart cart = cartRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found the cart "
                        + "with id " + userId)
                );
        return cartMapper.toCartDto(cart);
    }

    @Override
    public ShoppingCartDto createCartItem(
            CreateCartItemDto createCartItemDto,
            Authentication authentication
    ) {
        Long bookId = createCartItemDto.getBookId();
        Book bookById = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found the book "
                        + "with id " + bookId)
                );
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        CartItem existingCartItem = cartItemRepository.findByCartIdAndBookId(userId, bookId);
        if (existingCartItem != null) {
            throw new ProceedingException("You can not add more than one cart item "
                    + "type to the your cart. You can update its quantity");
        }
        CartItem cartItem = cartItemMapper.toEntity(createCartItemDto);
        cartItem.setBook(bookById);
        cartItem.setShoppingCart(new ShoppingCart(userId));
        cartItemRepository.save(cartItem);
        return getCart(authentication);
    }

    @Override
    public ShoppingCartDto updateCartItem(
            Long cartItemId,
            UpdateCartItemsQuantityDto requestDto,
            Authentication authentication
    ) {
        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found the cart item "
                        + "with id " + cartItemId)
                );
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return getCart(authentication);
    }

    @Override
    public ShoppingCartDto deleteCartItem(Long cartItemId, Authentication authentication) {
        Long userId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName())).getId();
        CartItem existingCartItem = Optional.ofNullable(cartItemRepository
                .findByIdAndByCartId(userId, cartItemId)).orElseThrow(
                () -> new EntityNotFoundException(
                        "Not found the cart item "
                        + "with id " + cartItemId + " in your shopping cart"
                )
        );
        cartItemRepository.delete(existingCartItem);
        return getCart(authentication);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        cartMapper.toCartDto(cartRepository.save(shoppingCart));
    }
}
