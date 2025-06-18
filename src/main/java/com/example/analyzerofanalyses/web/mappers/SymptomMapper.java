package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.web.dto.symptom.SymptomDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SymptomMapper extends Mappable<Symptom, SymptomDto> {
}
