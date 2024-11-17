package com.example.ordermanagmentservice.mapper;

import com.example.ordermanagmentservice.dto.OrderDto;
import com.example.ordermanagmentservice.dto.ProductDto;
import com.example.ordermanagmentservice.model.Order;
import com.example.ordermanagmentservice.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(source = "id",target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "product", target = "product")
    OrderDto orderToOrderDto(Order order);


    @Mapping(source = "id",target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "product", target = "product")
    Order orderDtoToOrder(OrderDto orderDto);


    List<OrderDto> ordersToOrderDto(List<Order> orderList);

    List<Order> orderDtosToOrders(List<OrderDto> orderDtoList);
}
