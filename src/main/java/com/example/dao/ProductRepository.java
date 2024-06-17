package com.example.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findByPnameContaining(String pname);
}
