package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.cart.CartItemDto;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.dto.cart.ShoppingCartDto;
//import org.example.intro.mapper.impl.CartItemMapperImpl;
import org.example.intro.model.Book;
import org.example.intro.model.CartItem;
import org.example.intro.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class})
public interface CartMapper {
//    @Mapping(target = "bookId", source = "id")
//    @Mapping(
//            target = "bookTitle",
//            source = "book",
//            qualifiedByName = "bookFromId"
//    )
//    CartItemDto toCartItemDto(CartItem cartItem);

//    @AfterMapping
//    default void setBookTitle(
//            @MappingTarget CartItemDto cartItemDto,
//            Book book
//    ) {
//        cartItemDto.setBookTitle(book.getTitle());
//    }

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems", source = "shoppingCart", qualifiedByName = "cartItemsDto")
    ShoppingCartDto toCartDto(ShoppingCart shoppingCart);

//    @AfterMapping
//    default void setCartItems(
//            @MappingTarget ShoppingCartDto shoppingCartDto,
//            ShoppingCart shoppingCart
//    ) {
//        List<CartItem> list = shoppingCart.getCartItems().stream()
//                .map(item -> item)
//                .toList();
//        System.out.println("jasdgjasd: " + list);
////        shoppingCartDto.setCartItems(list);
//    }
}
