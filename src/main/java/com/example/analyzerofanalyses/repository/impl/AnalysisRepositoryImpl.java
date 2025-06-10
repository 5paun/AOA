package com.example.analyzerofanalyses.repository.impl;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.repository.AnalysisRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AnalysisRepositoryImpl implements AnalysisRepository {
    @Override
    public Optional<Analysis> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Analysis> findAllByUserId(Long userId) {
        return List.of();
    }

    @Override
    public void assignToUserById(Long analysisId, Long userId) {

    }

    @Override
    public void update(Analysis analysis) {

    }

    @Override
    public void create(Analysis analysis) {

    }

    @Override
    public void delete(Long id) {

    }
}
