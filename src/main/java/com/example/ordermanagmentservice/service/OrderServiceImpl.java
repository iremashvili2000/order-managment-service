package com.example.ordermanagmentservice.service;

import com.example.ordermanagmentservice.model.Order;
import com.example.ordermanagmentservice.model.Status;
import com.example.ordermanagmentservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    @Cacheable("orders")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Cacheable(value = "order", key = "#id")
    public Order getOrderById(Long id) {
        // Use Optional to handle null checks
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "orders", allEntries = true)
    public Order createOrder(Order orderDtoToOrder) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        // Format for date: "dd-MM-yyyy"
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        orderDtoToOrder.setStatus(Status.IN_PROCESS);
        orderDtoToOrder.setCreated(currentDateTime.format(dateFormatter));
        return orderRepository.save(orderDtoToOrder);
    }

    @Override
    public void delete(Long id) {
        // Use Optional to check if the order exists before deleting
        orderRepository.findById(id).ifPresentOrElse(
                order -> {
                    orderRepository.delete(order);
                    log.info("Order deleted with id: {}", id);
                },
                () -> {
                    throw new RuntimeException("Order not found with id: " + id);
                }
        );
    }

    @Override
    @CacheEvict(value = "order", key = "#id")
    public Order updateOrder(Long id, Order orderDtoToOrder) {
        // Check if the order exists and then update it
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    // Update fields as needed
                    existingOrder.setPrice(orderDtoToOrder.getPrice());
                    existingOrder.setQuantity(orderDtoToOrder.getQuantity());
                    existingOrder.setStatus(orderDtoToOrder.getStatus());
                    existingOrder.setUserId(orderDtoToOrder.getUserId());
                    return orderRepository.save(existingOrder);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }


    @Override
    @CacheEvict(value = "order", key = "#id")
    public boolean deleteOrder(Long id) {
        // Check if the order exists before deleting and return a boolean
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
            log.info("Order deleted with id: {}", id);
            return true;
        } else {
            log.warn("Order not found with id: {}", id);
            return false;
        }
    }
}
