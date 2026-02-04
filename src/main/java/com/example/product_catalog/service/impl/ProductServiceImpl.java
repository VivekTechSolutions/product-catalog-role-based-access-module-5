package com.example.product_catalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.product_catalog.dto.request.ProductRequest;
import com.example.product_catalog.dto.response.ProductResponse;
import com.example.product_catalog.entity.Product;
import com.example.product_catalog.exception.ProductAccessDeniedException;
import com.example.product_catalog.exception.ProductNotFoundException;
import com.example.product_catalog.repository.ProductRepository;
import com.example.product_catalog.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // =========================
    // Create Product (ADMIN)
    // =========================
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        checkAdminRole();  
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    // ============================================
    // Get All Products (PUBLIC FOR USER AND ADMIN)
    // ============================================
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // Get Product By ID
    // =========================
    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return mapToResponse(product);
    }

    // =========================
    // Update Product (ADMIN)
    // =========================
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        checkAdminRole();  
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    // =========================
    // Delete Product (ADMIN)
    // =========================
    @Override
    public void deleteProduct(Long id) {
        checkAdminRole();  // Only admin can delete
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    // =========================
    // Mapper
    // =========================
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    // =========================
    // Role Check Helper
    // =========================
    private void checkAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().stream()
                .noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ProductAccessDeniedException("Access denied: Only admins can perform this action");
        }
    }
}
