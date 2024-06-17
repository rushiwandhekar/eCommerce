package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
}
