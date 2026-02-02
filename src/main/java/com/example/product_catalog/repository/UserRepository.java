package com.example.product_catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_catalog.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
