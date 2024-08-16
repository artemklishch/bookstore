package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.order.OrderItemDto;
import org.example.intro.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);
}
