package com.example.analyzerofanalyses.web.controller;

import com.example.analyzerofanalyses.domain.image.Image;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.service.SymptomService;
import com.example.analyzerofanalyses.web.dto.Image.ImageDto;
import com.example.analyzerofanalyses.web.dto.symptom.SymptomDto;
import com.example.analyzerofanalyses.web.dto.validation.OnCreate;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.example.analyzerofanalyses.web.mappers.ImageMapper;
import com.example.analyzerofanalyses.web.mappers.SymptomMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/symptoms")
@RequiredArgsConstructor
@Validated
@Tag(name = "Symptom controller", description = "Symptom API")
public class SymptomController {
    private final SymptomService symptomService;

    private final SymptomMapper symptomMapper;
    private final ImageMapper imageMapper;

    @PostMapping
    @Operation(summary = "Create symptom")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom()")
    public SymptomDto create(
            @Validated(OnCreate.class) @RequestBody final SymptomDto dto
    ) {
        Symptom symptom = symptomMapper.toEntity(dto);
        Symptom updatedSymptom = symptomService.create(symptom);

        return symptomMapper.toDto(updatedSymptom);
    }

    @PutMapping
    @Operation(summary = "Update symptom")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom()")
    public SymptomDto update(
            @Validated(OnUpdate.class) @RequestBody final SymptomDto dto
    ) {
        Symptom symptom = symptomMapper.toEntity(dto);
        Symptom updatedSymptom = symptomService.update(symptom);

        return symptomMapper.toDto(updatedSymptom);
    }

    @PatchMapping
    @Operation(summary = "Partially update symptom")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom()")
    public SymptomDto partialUpdate(@RequestBody final SymptomDto dto) {
        Symptom symptom = symptomMapper.toEntity(dto);
        Symptom updatedSymptom = symptomService.partialUpdate(symptom);

        return symptomMapper.toDto(updatedSymptom);
    }

    @GetMapping
    @Operation(summary = "Get All SymptomDto")
    public List<SymptomDto> getAll() {
        List<Symptom> symptoms = symptomService.getAll();

        return symptomMapper.toDto(symptoms);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get SymptomDto by id")
    public SymptomDto getById(@PathVariable final Long id) {
        Symptom symptom = symptomService.getById(id);

        return symptomMapper.toDto(symptom);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete symptom user")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom()")
    public void deleteById(@PathVariable final Long id) {
        symptomService.delete(id);
    }

    @PostMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image to symptom")
    @PreAuthorize("@customSecurityExpression.canAccessSymptom()")
    public void uploadImage(
            @PathVariable final Long id,
            @Validated @ModelAttribute final ImageDto imageDto
    ) {
        Image image = imageMapper.toEntity(imageDto);
        symptomService.uploadImage(id, image);
    }
}
