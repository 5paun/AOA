package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.analysis.AnalysisImage;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnalysisImageMapper extends Mappable<AnalysisImage, AnalysisImageDto> {
}
