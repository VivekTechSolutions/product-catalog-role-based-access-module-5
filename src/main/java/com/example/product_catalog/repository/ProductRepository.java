package com.example.product_catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_catalog.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}