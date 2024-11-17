package com.example.ordermanagmentservice.controller;


import com.example.ordermanagmentservice.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Orders", description = "API for managing orders")
@RequestMapping("/api/orders")
public interface OrderControllerInterface {


    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders();

    @Operation(summary = "Get order by ID", description = "Retrieve the details of an order by its ID")
    @GetMapping("/{id}")
    ResponseEntity<OrderDto> getOrderById(
            @Parameter(description = "ID of the order to retrieve", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Create a new order", description = "Create a new order with the provided details")
    @PostMapping
    ResponseEntity<OrderDto> createOrder(
            @Parameter(description = "Order details", required = true)
            @RequestBody @Valid  OrderDto orderDto);

    @Operation(summary = "Update an existing order", description = "Update the details of an existing order by its ID")
    @PutMapping("/{id}")
    ResponseEntity<OrderDto> updateOrder(
            @Parameter(description = "ID of the order to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated order details", required = true)
            @RequestBody @Valid OrderDto orderDto);

    @Operation(summary = "Delete an order", description = "Delete an order by its ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(
            @Parameter(description = "ID of the order to delete", required = true, example = "1")
            @PathVariable Long id);


}
