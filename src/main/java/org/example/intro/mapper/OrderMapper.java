package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.order.CreateOrderDto;
import org.example.intro.dto.order.OrderDto;
import org.example.intro.model.Order;
import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.springframework.data.jpa.repository.EntityGraph;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
//    @Mapping(target = "shippingAddress", source = "shippingAddress")
//    Order toEntity(CreateOrderDto requestDto);

//    @EntityGraph(attributePaths = "order")
//    @Mapping(target = "userId", source = "order.user.id")
//    @Mapping(target = "orderData", source = "order.orderDate")
//    @Mapping(target = "orderItems", source = "order.orderItems")
//    OrderDto toDto(Order order);
}
