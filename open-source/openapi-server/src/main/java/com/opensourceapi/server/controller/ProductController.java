package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.ProductRequest;
import com.opensourceapi.server.entity.Product;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Mock e-commerce catalog — great for practicing product listing/detail/cart-style UIs")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    @Operation(summary = "List all products, optionally filtered by category (public)")
    public List<Product> all(@RequestParam(required = false) String category) {
        if (category != null && !category.isBlank()) {
            return productRepository.findByCategoryIgnoreCase(category);
        }
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by id (public)")
    public Product getOne(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a product (requires auth)")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .stock(request.getStock() == null ? 0 : request.getStock())
                .imageUrl(request.getImageUrl())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a product (requires auth)")
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found", HttpStatus.NOT_FOUND));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock() == null ? product.getStock() : request.getStock());
        product.setImageUrl(request.getImageUrl());
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a product (requires auth)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found", HttpStatus.NOT_FOUND));
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
