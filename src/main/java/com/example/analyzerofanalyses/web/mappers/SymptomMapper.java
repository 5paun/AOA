package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.web.dto.symptom.SymptomDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface SymptomMapper {
    SymptomDto toDto(Symptom symptom);

    List<SymptomDto> toDto(List<Symptom> symptoms);

    Symptom toEntity(SymptomDto symptomDto);
}
