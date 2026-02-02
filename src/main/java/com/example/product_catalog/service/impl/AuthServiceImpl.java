package com.example.product_catalog.service.impl;

import com.example.product_catalog.dto.request.AuthRequest;
import com.example.product_catalog.dto.response.AuthResponse;
import com.example.product_catalog.entity.User;
import com.example.product_catalog.repository.UserRepository;
import com.example.product_catalog.service.AuthService;
import com.example.product_catalog.util.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

  
    // Register
    
    @Override
    public AuthResponse register(AuthRequest request) {

        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER"); // default role

        userRepository.save(user);

        // Usually token is not returned on register (industry practice)
        return new AuthResponse("User registered successfully");
    }

    // =========================
    // Login
    // =========================
    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Generate JWT
        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new AuthResponse(token);
    }
}
