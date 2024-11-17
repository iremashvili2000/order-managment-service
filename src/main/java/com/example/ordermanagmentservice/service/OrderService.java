package com.example.ordermanagmentservice.service;

import com.example.ordermanagmentservice.dto.OrderDto;
import com.example.ordermanagmentservice.model.Order;

import java.net.URI;
import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    Order getOrderById(Long id);

    Order createOrder(Order orderDtoToOrder);

    void delete(Long id);

    Order updateOrder(Long id, Order orderDtoToOrder);

    boolean deleteOrder(Long id);
}
