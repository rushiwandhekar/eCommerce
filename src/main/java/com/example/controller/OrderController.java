package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.entity.User;
import com.example.service.OrderService;
import com.example.service.UserService;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody List<OrderItem> orderItems, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        return orderService.placeOrder(user, orderItems);
    }

    @GetMapping("/getOrders")
    public List<Order> getOrders(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        return orderService.getOrdersByUserId(user.getId());
    }

    @PutMapping("/updateOrderStatus/{orderId}")
    public void updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
    }
}
