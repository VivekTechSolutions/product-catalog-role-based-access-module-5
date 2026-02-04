package com.example.product_catalog.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.product_catalog.dto.request.AuthRequest;
import com.example.product_catalog.dto.response.AuthResponse;
import com.example.product_catalog.entity.User;
import com.example.product_catalog.enums.RoleEnum;
import com.example.product_catalog.exception.InvalidCredentialsException;
import com.example.product_catalog.exception.UserAlreadyExistsException;
import com.example.product_catalog.repository.UserRepository;
import com.example.product_catalog.service.AuthService;
import com.example.product_catalog.util.JwtUtil;

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

    // =========================
    // Register → default ROLE_USER or from request
    // =========================
    @Override
    public AuthResponse register(AuthRequest request) {

        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Username '" + request.getUsername() + "' is already registered");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set role from request if provided, otherwise default ROLE_USER
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        } else {
            user.setRole(RoleEnum.ROLE_USER);
        }

        userRepository.save(user);

        return new AuthResponse("User registered successfully with role " + user.getRole().name());
    }

    // =========================
    // Login → JWT issued with roles
    // =========================
    @Override
    public AuthResponse login(AuthRequest request) {

        // Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        // Generate JWT with role(s)
        String token = jwtUtil.generateToken(
                user.getUsername(),
             // convert RoleEnum to String
                List.of(user.getRole().name())  
        );

        return new AuthResponse(token);
    }
}
