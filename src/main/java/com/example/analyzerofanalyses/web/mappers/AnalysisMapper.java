package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnalysisMapper extends Mappable<Analysis, AnalysisDto> {
}
