package com.example.analyzerofanalyses.web.controller;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.service.AnalysisService;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisDto;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.example.analyzerofanalyses.web.mappers.AnalysisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analyses")
@RequiredArgsConstructor
@Validated
public class AnalysisController {
    private final AnalysisService analysisService;

    private final AnalysisMapper analysisMapper;

    @PutMapping
    public AnalysisDto update(@Validated(OnUpdate.class) @RequestBody AnalysisDto dto) {
        Analysis analysis = analysisMapper.toEntity(dto);
        Analysis updatedAnalysis = analysisService.update(analysis);

        return analysisMapper.toDto(updatedAnalysis);
    }

    @GetMapping("/{id}")
    public AnalysisDto getById(@PathVariable Long id) {
        Analysis analysis = analysisService.getById(id);

        return analysisMapper.toDto(analysis);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        analysisService.delete(id);
    }
}
