package com.example.product_catalog.service;

import java.util.List;

import com.example.product_catalog.dto.request.ProductRequest;
import com.example.product_catalog.dto.response.ProductResponse;

public interface ProductService {

    // Admin only
   public  ProductResponse createProduct(ProductRequest request);

    // Public
    public List<ProductResponse> getAllProducts();

    // Public
    public  ProductResponse getProductById(Long id);

    // Admin only
    public ProductResponse updateProduct(Long id, ProductRequest request);

    // Admin only
    public  void deleteProduct(Long id);
}
