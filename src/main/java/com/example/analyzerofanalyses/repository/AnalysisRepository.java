package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.analysis.Analysis;

import java.util.List;
import java.util.Optional;

public interface AnalysisRepository {
    Optional<Analysis> findById(Long id);

    List<Analysis> findAllByUserId(Long userId);

    void assignToUserById(Long analysisId, Long userId);

    void update(Analysis analysis);

    void create(Analysis analysis);

    void delete(Long id);
}
