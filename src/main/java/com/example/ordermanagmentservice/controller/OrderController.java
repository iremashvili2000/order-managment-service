package com.example.ordermanagmentservice.controller;

import com.example.ordermanagmentservice.dto.OrderDto;
import com.example.ordermanagmentservice.mapper.OrderMapper;
import com.example.ordermanagmentservice.model.Order;
import com.example.ordermanagmentservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController  implements OrderControllerInterface {


    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, String> kafkaTemplate; // Inject KafkaTemplate

    @Override
    @Cacheable("orders") // Cache the result of getOrders
    public ResponseEntity<List<OrderDto>> getOrders() {
        log.info("OrderController starting getOrders method");
        return ResponseEntity.ok(orderMapper.ordersToOrderDto(orderService.getOrders()));
    }

    @Override
    @Cacheable(value = "order", key = "#id") // Cache each order by ID
    public ResponseEntity<OrderDto> getOrderById(Long id) {
        log.info("OrderController starting getOrderById method with Id {}", id);
        return ResponseEntity.ok(orderMapper.orderToOrderDto(orderService.getOrderById(id)));
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
        log.info("OrderController starting createOrder method {}", orderDto);
        Order createdOrder = orderService.createOrder(orderMapper.orderDtoToOrder(orderDto));

        // Send a Kafka message when an order is created
        kafkaTemplate.send("orderTopic", "Order Created: " + createdOrder.getId());

        return ResponseEntity.ok(orderMapper.orderToOrderDto(createdOrder));
    }

    @Override
    public ResponseEntity<OrderDto> updateOrder(Long id, OrderDto orderDto) {
        log.info("OrderController starting updateOrder method with Id {} and object: {}", id, orderDto);
        Order updatedOrder = orderService.updateOrder(id, orderMapper.orderDtoToOrder(orderDto));

        // Send a Kafka message when an order is updated
        kafkaTemplate.send("orderTopic", "Order Updated: " + updatedOrder.getId());


        return ResponseEntity.ok(orderMapper.orderToOrderDto(updatedOrder));
    }

    @Override
    @CacheEvict(value = "order", key = "#id") // Evict cache when an order is deleted
    public ResponseEntity<Void> deleteOrder(Long id) {
        log.info("OrderController starting deleteOrder method with id {}", id);
        boolean isDeleted = orderService.deleteOrder(id);

        if (isDeleted) {
            // Send a Kafka message when an order is deleted
            kafkaTemplate.send("orderTopic", "Order Deleted: " + id);
            return ResponseEntity.noContent().build(); // No content, 204 status
        } else {
            return ResponseEntity.status(404).build(); // 404 status if not found
        }
    }
}
