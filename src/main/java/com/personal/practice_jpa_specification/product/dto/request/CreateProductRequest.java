package com.personal.practice_jpa_specification.product.dto.request;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        String categoryCode,
        BigDecimal price,
        Boolean inStock,
        BigDecimal rating
) {}