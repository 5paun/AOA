package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.web.dto.filter.AnalysisFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AnalysisSpecificationBuilder implements SpecificationBuilder<AnalysisFilter, Analysis>, AnalysisSpecification {

    @Override
    public Specification<Analysis> getSpecification(AnalysisFilter searchRequest) {
        return belongsToUser(searchRequest.getClientId())
                .and(hasTitle(searchRequest.getTitle()))
                .and(totalCholesterolFrom(searchRequest.getTotalCholesterolFrom()))
                .and(totalCholesterolTo(searchRequest.getTotalCholesterolTo()))
                .and(whiteBloodCellsFrom(searchRequest.getWhiteBloodCellsFrom()))
                .and(whiteBloodCellsTo(searchRequest.getWhiteBloodCellsTo()))
                .and(lymphocytesFrom(searchRequest.getLymphocytesFrom()))
                .and(lymphocytesTo(searchRequest.getLymphocytesTo()))
                .and(createdDateFrom(searchRequest.getCreatedDateFrom()))
                .and(createdDateTo(searchRequest.getCreatedDateTo()))
                .and(hasImage(searchRequest.getHasImage()));
    }

}
