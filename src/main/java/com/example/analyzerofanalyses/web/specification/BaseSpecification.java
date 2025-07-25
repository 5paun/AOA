package com.example.analyzerofanalyses.web.specification;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BaseSpecification<E> {
    
    default Specification<E> belongsToUser(Long clientId) {
        return ((root, query, criteriaBuilder) ->
                clientId == null
                        ? null
                        : criteriaBuilder.equal(root.get("user").get("id"), clientId));

    }

    default Specification<E> hasStringField(String value, String field) {
        return (root, query, criteriaBuilder) ->
                value == null
                        ? null
                        : criteriaBuilder.like(root.get(field), "%" + value + "%");
    }

    default <T> Specification<E> greaterThanOrEqual(T value, String field) {
        return (root, query, criteriaBuilder) ->
                switch (value) {
                    case null -> null;
                    case Integer i -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), i);
                    case BigDecimal bigDecimal -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), bigDecimal);
                    case LocalDateTime localDateTime -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), localDateTime);
                    case LocalDate localDate -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), localDate);
                    default -> throw new IllegalArgumentException(("Unsupported type: " + value.getClass().getName()));
                };
    }

    default <T> Specification<E> lessThanOrEqual(T value, String field) {
        return (root, query, criteriaBuilder) ->
            switch (value) {
                case null -> null;
                case Integer i -> criteriaBuilder.lessThanOrEqualTo(root.get(field), i);
                case BigDecimal bigDecimal -> criteriaBuilder.lessThanOrEqualTo(root.get(field), bigDecimal);
                case LocalDateTime localDateTime -> criteriaBuilder.lessThanOrEqualTo(root.get(field), localDateTime);
                case LocalDate localDate -> criteriaBuilder.lessThanOrEqualTo(root.get(field), localDate);
                default -> throw new IllegalArgumentException(("Unsupported type: " + value.getClass().getName()));
        };
    }

    default Specification<E> hasImage(Boolean booleanValue) {
        return (root, query, criteriaBuilder) -> {
            if (booleanValue == null) {
                return null;
            }

            return booleanValue
                    ? criteriaBuilder.greaterThan(criteriaBuilder.size(root.get("images")), 0)
                    : criteriaBuilder.equal(criteriaBuilder.size(root.get("images")), 0);
        };
    }

}
