package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.cart.CartItemDto;
import org.example.intro.dto.cart.CreateCartItemDto;
import org.example.intro.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface CartItemMapper {
    CartItem toEntity(CreateCartItemDto requestDto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toCartItemDto(CartItem cartItem);
}
