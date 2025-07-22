package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AnalysisSpecification extends BaseSpecification<Analysis> {

    default Specification<Analysis> hasTitle(String value) {
        return hasStringField(value, "title");
    }

    default Specification<Analysis> totalCholesterolFrom(BigDecimal totalCholesterolFrom) {
        return greaterThanOrEqual(totalCholesterolFrom, "totalCholesterol");
    }

    default Specification<Analysis> totalCholesterolTo(BigDecimal totalCholesterolTo) {
        return lessThanOrEqual(totalCholesterolTo, "totalCholesterol");
    }

    default Specification<Analysis> whiteBloodCellsFrom(BigDecimal whiteBloodCellsFrom) {
        return greaterThanOrEqual(whiteBloodCellsFrom, "whiteBloodCells");
    }

    default Specification<Analysis> whiteBloodCellsTo(BigDecimal whiteBloodCellsTo) {
        return lessThanOrEqual(whiteBloodCellsTo, "whiteBloodCells");
    }

    default Specification<Analysis> lymphocytesFrom(Integer lymphocytesFrom) {
        return greaterThanOrEqual(lymphocytesFrom, "lymphocytes");
    }

    default Specification<Analysis> lymphocytesTo(Integer lymphocytesTo) {
        return lessThanOrEqual(lymphocytesTo, "lymphocytes");
    }

    default Specification<Analysis> createdDateFrom(LocalDateTime createdDateFrom) {
        return greaterThanOrEqual(createdDateFrom, "createdDate");
    }

    default Specification<Analysis> createdDateTo(LocalDateTime createdDateTo) {
        return lessThanOrEqual(createdDateTo, "createdDate");
    }

}
