package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AnalysisSpecification extends BaseSpecification {

    public static Specification<Analysis> belongsToUser (Long userId) {
        return BaseSpecification.belongsToUser(userId);
    }

    public static Specification<Analysis> hasTitle(String value) {
        return hasStringField(value, "title");
    }

    public static Specification<Analysis> totalCholesterolFrom(BigDecimal totalCholesterolFrom) {
        return greaterThanOrEqual(totalCholesterolFrom, "totalCholesterol");
    }

    public static Specification<Analysis> totalCholesterolTo(BigDecimal totalCholesterolTo) {
        return lessThanOrEqual(totalCholesterolTo, "totalCholesterol");
    }

    public static Specification<Analysis> whiteBloodCellsFrom(BigDecimal whiteBloodCellsFrom) {
        return greaterThanOrEqual(whiteBloodCellsFrom, "whiteBloodCells");
    }

    public static Specification<Analysis> whiteBloodCellsTo(BigDecimal whiteBloodCellsTo) {
        return lessThanOrEqual(whiteBloodCellsTo, "whiteBloodCells");
    }

    public static Specification<Analysis> lymphocytesFrom(Integer lymphocytesFrom) {
        return greaterThanOrEqual(lymphocytesFrom, "lymphocytes");
    }

    public static Specification<Analysis> lymphocytesTo(Integer lymphocytesTo) {
        return lessThanOrEqual(lymphocytesTo, "lymphocytes");
    }

    public static Specification<Analysis> createdDateFrom(LocalDateTime createdDateFrom) {
        return greaterThanOrEqual(createdDateFrom, "createdDate");
    }

    public static Specification<Analysis> createdDateTo(LocalDateTime createdDateTo) {
        return lessThanOrEqual(createdDateTo, "createdDate");
    }

    public static Specification<Analysis> hasImage (Boolean booleanValue) {
        return BaseSpecification.hasImage(booleanValue);
    }

}
