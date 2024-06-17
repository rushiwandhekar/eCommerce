package com.example.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
