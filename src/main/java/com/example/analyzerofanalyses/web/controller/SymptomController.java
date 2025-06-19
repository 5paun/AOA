package com.example.analyzerofanalyses.web.controller;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.domain.symptom.SymptomImage;
import com.example.analyzerofanalyses.service.SymptomService;
import com.example.analyzerofanalyses.web.dto.symptom.SymptomDto;
import com.example.analyzerofanalyses.web.dto.symptom.SymptomImageDto;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.example.analyzerofanalyses.web.mappers.SymptomImageMapper;
import com.example.analyzerofanalyses.web.mappers.SymptomMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/symptoms")
@RequiredArgsConstructor
@Validated
@Tag(name = "Symptom controller", description = "Symptom API")
public class SymptomController {
    private final SymptomService symptomService;

    private final SymptomMapper symptomMapper;
    private final SymptomImageMapper symptomImageMapper;

    @PutMapping
    @Operation(summary = "Update symptom")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom(#dto.id)")
    public SymptomDto update(@Validated(OnUpdate.class) @RequestBody SymptomDto dto) {
        Symptom symptom = symptomMapper.toEntity(dto);
        Symptom updatedSymptom = symptomService.update(symptom);

        return symptomMapper.toDto(updatedSymptom);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get SymptomDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom(#id)")
    public SymptomDto getById(@PathVariable Long id) {
        Symptom symptom = symptomService.getById(id);

        return symptomMapper.toDto(symptom);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete symptom user")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom(#id)")
    public void deleteById(@PathVariable Long id) {
        symptomService.delete(id);
    }

    @PostMapping("/{id}/image")
    @Operation(summary = "Upload image to symptom")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom(#id)")
    public void uploadImage(@PathVariable Long id, @Validated @ModelAttribute SymptomImageDto imageDto) {
        SymptomImage image = symptomImageMapper.toEntity(imageDto);
        symptomService.uploadImage(id, image);
    }
}
