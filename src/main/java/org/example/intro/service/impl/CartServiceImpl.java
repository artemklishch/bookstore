package org.example.intro.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.cart.CartItemDto;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.dto.cart.ShoppingCartDto;
import org.example.intro.dto.cart.UpdateCartItemsQuantityDto;
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
import org.example.intro.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CustomUserDetailsService userDetailsService;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getCart(
            Authentication authentication,
            Pageable pageable
    ) {
        Long cartId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName()))
                .getShoppingCart().getId();
        ShoppingCart shoppingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Page<CartItem> cartItemPage = cartItemRepository.findAllByShoppingCartId(
                shoppingCart.getId(), pageable
        );
        List<CartItemDto> cartItems = cartItemPage.stream()
                .map(cartItemMapper::toCartItemDto)
                .toList();
        ShoppingCartDto shoppingCartDto = cartMapper.toCartDto(shoppingCart);
        shoppingCartDto.setCartItems(cartItems);
        return shoppingCartDto;
    }

    @Override
    public CartItemDto createCartItem(
            CreateCartItemDto createCartItemDto,
            Authentication authentication
    ) {
        Long bookId = createCartItemDto.getBookId();
        Book bookById = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
        Long cartId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName()))
                .getShoppingCart().getId();
        CartItem existingCartItem = cartItemRepository.findByCartIdAndBookId(cartId, bookId);
        if (existingCartItem != null) {
            throw new RuntimeException("You can not add more than one cart item " +
                    "type to the your cart. You can update its quantity");
        }
        CartItem cartItem = cartItemMapper.toEntity(createCartItemDto);
        cartItem.setBook(bookById);
        cartItem.setShoppingCart(new ShoppingCart(cartId));
        return cartItemMapper.toCartItemDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateCartItem(
            Long cartItemId,
            UpdateCartItemsQuantityDto requestDto
    ) {
        CartItem cartItemFromDb = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("Cart item not found"));
        cartItemFromDb.setQuantity(requestDto.getQuantity());
        return cartItemMapper.toCartItemDto(cartItemRepository.save(cartItemFromDb));
    }

    @Override
    public void deleteCartItem(Long cartItemId, Authentication authentication) {
        Long cartId = ((User) userDetailsService
                .loadUserByUsername(authentication.getName()))
                .getShoppingCart().getId();
        CartItem existingCartItem = Optional.ofNullable(cartItemRepository
                .findByIdAndByCartId(cartId, cartItemId)).orElseThrow(
                () -> new NoSuchElementException(
                        "No such item found in your shopping cart"
                )
        );
        cartItemRepository.delete(existingCartItem);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        cartMapper.toCartDto(cartRepository.save(shoppingCart));
    }
}
