package com.personal.practice_jpa_specification.product.dto.request;

import java.math.BigDecimal;

public record ProductFilterRequest(
        String name,
        String categoryCode,
        BigDecimal minPrice,
        Boolean inStock,
        BigDecimal minRating
) {}
