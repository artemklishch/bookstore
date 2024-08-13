package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.cart.CartItemDto;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.model.Book;
import org.example.intro.model.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface CartItemMapper {
    @Mapping(target = "book", ignore = true)
    CartItem toEntity(CreateCartItemDto requestDto);

    @AfterMapping
    default void setBookId(
            @MappingTarget CartItem cartItem,
            CreateCartItemDto requestDto
    ){
        cartItem.setBook(new Book(requestDto.getBookId()));
    }

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(
            target = "bookTitle",
            source = "book.title"
    )
    CartItemDto toCartItemDto(CartItem cartItem);
}
