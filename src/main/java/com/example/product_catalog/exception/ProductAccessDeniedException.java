package com.example.product_catalog.exception;

public class ProductAccessDeniedException extends RuntimeException {
    public ProductAccessDeniedException(String message) {
        super(message);
    }
}