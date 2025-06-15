package com.example.analyzerofanalyses.web.controller;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.service.AnalysisService;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisDto;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.example.analyzerofanalyses.web.mappers.AnalysisMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analyses")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Validated
@Tag(name = "Analysis controller", description = "Analysis API")
public class AnalysisController {
    private final AnalysisService analysisService;

    private final AnalysisMapper analysisMapper;

    @PutMapping
    @Operation(summary = "Update analysis")
    @PreAuthorize("@customSecurityExpression.canAccessAnalysis(#dto.id)")
    public AnalysisDto update(@Validated(OnUpdate.class) @RequestBody AnalysisDto dto) {
        Analysis analysis = analysisMapper.toEntity(dto);
        Analysis updatedAnalysis = analysisService.update(analysis);

        return analysisMapper.toDto(updatedAnalysis);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get AnalysisDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessAnalysis(#id)")
    public AnalysisDto getById(@PathVariable Long id) {
        Analysis analysis = analysisService.getById(id);

        return analysisMapper.toDto(analysis);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete analysis  user")
    @PreAuthorize("@customSecurityExpression.canAccessAnalysis(#id)")
    public void deleteById(@PathVariable Long id) {
        analysisService.delete(id);
    }
}
