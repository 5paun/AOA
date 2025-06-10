package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface AnalysisMapper {
    AnalysisDto toDto(Analysis analysis);

    List<AnalysisDto> toDto(List<Analysis> analyses);

    Analysis toEntity(AnalysisDto analysisDto);
}
