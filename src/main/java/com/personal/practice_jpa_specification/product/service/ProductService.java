package com.personal.practice_jpa_specification.product.service;

import com.personal.practice_jpa_specification.category.entity.Category;
import com.personal.practice_jpa_specification.category.repository.CategoryRepository;
import com.personal.practice_jpa_specification.product.dto.request.CreateProductRequest;
import com.personal.practice_jpa_specification.product.dto.request.ProductFilterRequest;
import com.personal.practice_jpa_specification.product.entity.Product;
import com.personal.practice_jpa_specification.product.repository.ProductRepository;
import com.personal.practice_jpa_specification.product.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product createProduct(CreateProductRequest request) {
        // 1. Fetch category entity by code (Assume CategoryRepository exists)
        Category category = categoryRepository.findByCode(request.categoryCode())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with code: " + request.categoryCode()));

        // 2. Map DTO to Entity
        Product product = Product.builder()
                .name(request.name())
                .category(category)
                .price(request.price())
                .inStock(request.inStock())
                .rating(request.rating())
                .build();

        // 3. Save to database (@PrePersist automatically sets createdAt and updatedAt)
        return productRepository.save(product);
    }

    public Page<Product> searchProduct(ProductFilterRequest filter, Pageable pageable) {

        Specification<Product> spec = Specification
                .where(ProductSpecification.hasName(filter.name()))
                .and(ProductSpecification.hasCategoryCode(filter.categoryCode()))
                .and(ProductSpecification.hasMinRating(filter.minRating()))
                .and(ProductSpecification.hasInStock(filter.inStock()))
                .and(ProductSpecification.hasMinPrice(filter.minPrice()));

        return productRepository.findAll(spec, pageable);

    }

}
