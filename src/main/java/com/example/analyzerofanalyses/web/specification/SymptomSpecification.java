package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import org.springframework.data.jpa.domain.Specification;

public class SymptomSpecification {

    private SymptomSpecification() {}

    public static Specification<Symptom> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Symptom> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> description == null ? null : criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Symptom> hasRecommendation(String recommendation) {
        return (root, query, criteriaBuilder) -> recommendation == null ? null : criteriaBuilder.like(root.get("recommendation"), "%" + recommendation + "%");
    }

    public static Specification<Symptom> hasImage(Boolean booleanValue) {
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
