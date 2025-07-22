package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import org.springframework.data.jpa.domain.Specification;

public interface SymptomSpecification extends BaseSpecification<Symptom> {

    default Specification<Symptom> hasTitle(String value) {
        return hasStringField(value, "title");
    }

    default Specification<Symptom> hasDescription(String value) {
        return hasStringField(value, "description");
    }

    default Specification<Symptom> hasRecommendation(String value) {
        return hasStringField(value, "recommendation");
    }

}
