package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.symptom.SymptomImage;
import com.example.analyzerofanalyses.web.dto.symptom.SymptomImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SymptomImageMapper extends Mappable<SymptomImage, SymptomImageDto> {
}
