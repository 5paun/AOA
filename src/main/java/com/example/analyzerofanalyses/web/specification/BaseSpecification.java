package com.example.analyzerofanalyses.web.specification;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BaseSpecification {

    protected BaseSpecification() {}

    public static <E> Specification<E> belongsToUser(Long userId) {
        return ((root, query, criteriaBuilder) ->
                userId == null
                        ? null
                        : criteriaBuilder.equal(root.get("user").get("id"), userId));

    }

    public static <E> Specification<E> hasStringField(String value, String field) {
        return (root, query, criteriaBuilder) ->
                value == null
                        ? null
                        : criteriaBuilder.like(root.get(field), "%" + value + "%");
    }

    public static <T, E> Specification<E> greaterThanOrEqual(T value, String field) {
        return (root, query, criteriaBuilder) ->
                switch (value) {
                    case null -> null;
                    case Integer i -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), i);
                    case BigDecimal bigDecimal -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), bigDecimal);
                    case LocalDateTime localDateTime -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), localDateTime);
                    default -> throw new IllegalArgumentException(("Unsupported type: " + value.getClass().getName()));
                };
    }

    public static <T, E> Specification<E> lessThanOrEqual(T value, String field) {
        return (root, query, criteriaBuilder) ->
            switch (value) {
                case null -> null;
                case Integer i -> criteriaBuilder.lessThanOrEqualTo(root.get(field), i);
                case BigDecimal bigDecimal -> criteriaBuilder.lessThanOrEqualTo(root.get(field), bigDecimal);
                case LocalDateTime localDateTime -> criteriaBuilder.lessThanOrEqualTo(root.get(field), localDateTime);
                default -> throw new IllegalArgumentException(("Unsupported type: " + value.getClass().getName()));
        };
    }

    public static <E> Specification<E> hasImage(Boolean booleanValue) {
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
