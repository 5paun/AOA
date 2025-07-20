package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import org.springframework.data.jpa.domain.Specification;

public class SymptomSpecification extends BaseSpecification {

    public static Specification<Symptom> hasTitle(String value) {
        return hasStringField(value, "title");
    }

    public static Specification<Symptom> hasDescription(String value) {
        return hasStringField(value, "description");
    }

    public static Specification<Symptom> hasRecommendation(String value) {
        return hasStringField(value, "recommendation");
    }

    public static Specification<Symptom> hasImage (Boolean booleanValue) {
        return BaseSpecification.hasImage(booleanValue);
    }

}
