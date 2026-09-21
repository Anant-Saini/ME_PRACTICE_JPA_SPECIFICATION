package com.personal.practice_jpa_specification.product.controller;

import com.personal.practice_jpa_specification.product.dto.request.CreateProductRequest;
import com.personal.practice_jpa_specification.product.dto.request.ProductFilterRequest;
import com.personal.practice_jpa_specification.product.entity.Product;
import com.personal.practice_jpa_specification.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Endpoints for managing and filtering products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
        Product createdProduct = productService.createProduct(request);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Search products with dynamic filters and pagination")
    public ResponseEntity<Page<Product>> searchProducts(
            @ModelAttribute ProductFilterRequest filterRequest,
            @PageableDefault(size = 10) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Product> products = productService.searchProduct(filterRequest, pageable);
        return ResponseEntity.ok(products);
    }
}
