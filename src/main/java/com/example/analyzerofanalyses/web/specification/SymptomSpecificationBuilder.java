package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.web.dto.filter.SymptomFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SymptomSpecificationBuilder implements SpecificationBuilder<SymptomFilter, Symptom>, SymptomSpecification {

    @Override
    public Specification<Symptom> getSpecification(SymptomFilter searchRequest) {
        return hasTitle(searchRequest.getTitle())
                .and(hasDescription(searchRequest.getDescription()))
                .and(hasRecommendation(searchRequest.getRecommendation()))
                .and(hasImage(searchRequest.getHasImage()));
    }

}
