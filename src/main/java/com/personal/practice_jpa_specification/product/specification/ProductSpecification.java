package com.personal.practice_jpa_specification.product.specification;

import com.personal.practice_jpa_specification.product.dto.request.ProductFilterRequest;
import com.personal.practice_jpa_specification.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductSpecification {

    public static Specification<Product> hasName(String name){
        return (root, query, cb) -> {
            if(name == null || name.isEmpty()){
               return cb.conjunction();
            }
            return cb.like(root.get("name").as(String.class), "%" + name + "%");
        };
    }
    public static Specification<Product> hasCategoryCode(String categoryCode){
        return (root, query, cb) -> {
            if(categoryCode == null || categoryCode.isEmpty()){
                return cb.conjunction();
            }
            return cb.equal(root.get("category").get("code").as(String.class), categoryCode);
        };
    }
    public static Specification<Product> hasMinPrice(BigDecimal minPrice){
        return (root, query, cb) -> {
            if(minPrice == null || minPrice.compareTo(BigDecimal.ZERO) < 0){
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("price").as(BigDecimal.class), minPrice);
        };
    }
    public static Specification<Product> hasInStock(Boolean inStock){
        return (root, query, cb) -> {
            if(inStock == null ){
                return cb.conjunction();
            }
            return cb.equal(root.get("inStock").as(Boolean.class), inStock);
        };
    }
    public static Specification<Product> hasMinRating(BigDecimal rating){
        return (root, query, cb) -> {
            if(rating == null){
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("rating").as(BigDecimal.class), rating);
        };
    }
}
