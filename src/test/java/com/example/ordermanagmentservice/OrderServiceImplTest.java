package com.example.ordermanagmentservice;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.example.ordermanagmentservice.model.Order;
import com.example.ordermanagmentservice.model.Status;
import com.example.ordermanagmentservice.repository.OrderRepository;
import com.example.ordermanagmentservice.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = new Order();
        sampleOrder.setId(1L);
        sampleOrder.setPrice(100.0);
        sampleOrder.setQuantity(2);
        sampleOrder.setStatus(Status.IN_PROCESS);
        sampleOrder.setUserId(123L);
    }

    @Test
    void testGetOrders() {
        // Arrange
        List<Order> orders = Arrays.asList(sampleOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> result = orderService.getOrders();

        // Assert
        assertEquals(1, result.size());
        assertEquals(sampleOrder, result.get(0));
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById_WhenOrderExists() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        // Act
        Order result = orderService.getOrderById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(sampleOrder, result);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_WhenOrderDoesNotExist() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> orderService.getOrderById(1L));
        assertEquals("Order not found with id: 1", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenReturn(sampleOrder);

        // Act
        Order result = orderService.createOrder(sampleOrder);

        // Assert
        assertNotNull(result);
        assertEquals(Status.IN_PROCESS, result.getStatus());
        verify(orderRepository, times(1)).save(sampleOrder);
    }

    @Test
    void testDeleteOrder_WhenOrderExists() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        // Act
        boolean result = orderService.deleteOrder(1L);

        // Assert
        assertTrue(result);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).delete(sampleOrder);
    }

    @Test
    void testDeleteOrder_WhenOrderDoesNotExist() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = orderService.deleteOrder(1L);

        // Assert
        assertFalse(result);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).delete(any(Order.class));
    }

    @Test
    void testUpdateOrder_WhenOrderExists() {
        // Arrange
        Order updatedOrder = new Order();
        updatedOrder.setPrice(150.0);
        updatedOrder.setQuantity(3);
        updatedOrder.setStatus(Status.FINISHED);
        updatedOrder.setUserId(123L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        // Act
        Order result = orderService.updateOrder(1L, updatedOrder);

        // Assert
        assertNotNull(result);
        assertEquals(150.0, result.getPrice());
        assertEquals(3, result.getQuantity());
        assertEquals(Status.FINISHED, result.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrder_WhenOrderDoesNotExist() {
        // Arrange
        Order updatedOrder = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> orderService.updateOrder(1L, updatedOrder));
        assertEquals("Order not found with id: 1", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }


    @Test
    public void testGetOrderById_OrderNotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(orderId);
        });

        // Assert the exception message
        assertEquals("Order not found with id: " + orderId, exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
    }
}