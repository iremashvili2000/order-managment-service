package com.example.ordermanagmentservice.repository;


import com.example.ordermanagmentservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {

}
