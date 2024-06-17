package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.OrderItemRepository;
import com.example.dao.OrderRepository;
import com.example.dao.ProductRepository;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.entity.Product;
import com.example.entity.User;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(User user, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        for (OrderItem orderItem : orderItems) {
        	 if (orderItem != null && orderItem.getProduct() != null) {
            Product product = productRepository.findById(orderItem.getProduct().getId()).orElse(null);
            if (product != null && product.getStock() >= orderItem.getQuantity()) {
                product.setStock(product.getStock() - orderItem.getQuantity());
                productRepository.save(product);

                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            } else {
                throw new RuntimeException("Product not available or out of stock");
            }
        	 } else {
                 throw new RuntimeException("OrderItem or its Product is null");
             }
        }
        return order;
    }
    

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    public void updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}
