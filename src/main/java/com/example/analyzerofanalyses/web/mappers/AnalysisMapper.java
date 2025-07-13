package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface AnalysisMapper extends Mappable<Analysis, AnalysisDto> {

    @Override
    @Mapping(target = "clientId", source = "user.id")
    AnalysisDto toDto(Analysis entity);

}
