package com.example.product_catalog.service;

import com.example.product_catalog.dto.request.AuthRequest;
import com.example.product_catalog.dto.response.AuthResponse;

public interface AuthService {
    
    // Register a new user
    AuthResponse register(AuthRequest request);

    // Login user and return JWT token
    AuthResponse login(AuthRequest request);
}