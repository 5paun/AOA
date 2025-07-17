package com.example.analyzerofanalyses.web.controller;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.image.Image;
import com.example.analyzerofanalyses.service.AnalysisService;
import com.example.analyzerofanalyses.web.dto.Image.ImageDto;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisDto;
import com.example.analyzerofanalyses.web.dto.filter.AnalysisFilter;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.example.analyzerofanalyses.web.mappers.AnalysisMapper;
import com.example.analyzerofanalyses.web.mappers.ImageMapper;
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
@RequestMapping("/api/v1/analyses")
@RequiredArgsConstructor
@Validated
@Tag(name = "Analysis controller", description = "Analysis API")
public class AnalysisController {
    private final AnalysisService analysisService;

    private final AnalysisMapper analysisMapper;
    private final ImageMapper imageMapper;

    @PutMapping
    @Operation(summary = "Update analysis")
    @PreAuthorize("@customSecurityExpression.isAnalysisOwner(#dto.id)")
    public AnalysisDto update(
            @Validated(OnUpdate.class) @RequestBody final AnalysisDto dto
    ) {
        analysisService.getById(dto.getId());
        Analysis analysis = analysisMapper.toEntity(dto);
        Analysis updatedAnalysis = analysisService.update(analysis);

        return analysisMapper.toDto(updatedAnalysis);
    }

    @PatchMapping
    @Operation(summary = "Partially update Analysis")
    @PreAuthorize("@customSecurityExpression.isAnalysisOwner(#dto.id)")
    public AnalysisDto partialUpdate(final AnalysisDto dto) {
        Analysis analysis = analysisMapper.toEntity(dto);
        Analysis updatedAnalysis = analysisService.partialUpdate(analysis);

        return analysisMapper.toDto(updatedAnalysis);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get AnalysisDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessAnalysis(#id)")
    public AnalysisDto getById(@PathVariable final Long id) {
        Analysis analysis = analysisService.getById(id);

        return analysisMapper.toDto(analysis);
    }

    @PostMapping("/search")
    @Operation(summary = "Search AnalysisDtos")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#searchRequest.userId)")
    public List<AnalysisDto> search(@RequestBody AnalysisFilter searchRequest) {
        List<Analysis> analyses = analysisService.search(searchRequest);

        return analysisMapper.toDto(analyses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete analysis  user")
    @PreAuthorize("@customSecurityExpression.isAnalysisOwner(#id)")
    public void deleteById(@PathVariable final Long id) {
        analysisService.delete(id);
    }

    @PostMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image to analysis")
    @PreAuthorize("@customSecurityExpression.isAnalysisOwner(#id)")
    public void uploadImage(
            @PathVariable final Long id,
            @Validated @ModelAttribute final ImageDto imageDto
    ) {
        Image image = imageMapper.toEntity(imageDto);
        analysisService.uploadImage(id, image);
    }
}
