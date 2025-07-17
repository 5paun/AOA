package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AnalysisSpecification {

    private AnalysisSpecification() {}

    public static Specification<Analysis> belongsToUser(Long userId) {
        return ((root, query, criteriaBuilder) ->
            userId == null
                ? null
                : criteriaBuilder.equal(root.get("user").get("id"), userId));

    }

    public static Specification<Analysis> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title == null
                        ? null
                        : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Analysis> totalCholesterolFrom(BigDecimal totalCholesterolFrom) {
        return (root, query, criteriaBuilder) ->
                totalCholesterolFrom == null
                    ? null
                    : criteriaBuilder.greaterThanOrEqualTo(root.get("totalCholesterol"), totalCholesterolFrom);
    }

    public static Specification<Analysis> totalCholesterolTo(BigDecimal totalCholesterolTo) {
        return (root, query, criteriaBuilder) ->
                totalCholesterolTo == null
                        ? null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("totalCholesterol"), totalCholesterolTo);
    }

    public static Specification<Analysis> whiteBloodCellsFrom(BigDecimal whiteBloodCellsFrom) {
        return (root, query, criteriaBuilder) ->
                whiteBloodCellsFrom == null
                        ? null
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("whiteBloodCells"), whiteBloodCellsFrom);
    }

    public static Specification<Analysis> whiteBloodCellsTo(BigDecimal whiteBloodCellsTo) {
        return (root, query, criteriaBuilder) ->
                whiteBloodCellsTo == null
                        ? null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("whiteBloodCells"), whiteBloodCellsTo);
    }

    public static Specification<Analysis> lymphocytesFrom(Integer lymphocytesFrom) {
        return (root, query, criteriaBuilder) ->
                lymphocytesFrom == null
                        ? null
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("lymphocytes"), lymphocytesFrom);
    }

    public static Specification<Analysis> lymphocytesTo(Integer lymphocytesTo) {
        return (root, query, criteriaBuilder) ->
                lymphocytesTo == null
                        ? null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("lymphocytes"), lymphocytesTo);
    }

    public static Specification<Analysis> createdDateFrom(LocalDateTime createdDateFrom) {
        return (root, query, criteriaBuilder) ->
                createdDateFrom == null
                        ? null
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), createdDateFrom);
    }

    public static Specification<Analysis> createdDateTo(LocalDateTime createdDateTo) {
        return (root, query, criteriaBuilder) ->
                createdDateTo == null
                        ? null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), createdDateTo);
    }

    public static Specification<Analysis> hasImage(Boolean booleanValue) {
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
