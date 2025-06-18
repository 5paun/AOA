package com.example.analyzerofanalyses.service;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.analysis.AnalysisImage;

import java.util.List;

public interface AnalysisService {
    Analysis getById(Long id);

    List<Analysis> getAllByUserId(Long id);

    Analysis update(Analysis analysis);

    Analysis create(Analysis analysis, Long userId);

    void delete(Long id);

    void uploadImage(Long id, AnalysisImage image);
}
