package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.cart.CartItemDto;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.model.Book;
import org.example.intro.model.CartItem;
import org.example.intro.model.ShoppingCart;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface CartItemMapper {
    CartItem toEntity(CreateCartItemDto requestDto);

    @Mapping(target = "bookId", source = "id")
    @Mapping(
            target = "bookTitle",
//            source = "book",
            ignore = true,
            qualifiedByName = "bookFromId"
    )
    CartItemDto toCartItemDto(CartItem cartItem);

//    @AfterMapping
//    default void setBookTitle(
//            @MappingTarget CartItemDto cartItemDto,
//            Book book
//    ) {
//        cartItemDto.setBookTitle(book.getTitle());
//    }

    @Named("cartItemsDto")
    default List<CartItemDto> cartItemsDto(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
//                .map(item -> {
//                    CartItemDto dto = this.toCartItemDto(item);
//                    dto.setBookTitle("JHGJHGJHG");
//                    return dto;
//                })
                .map(this::toCartItemDto)
                .toList();
    }
}
