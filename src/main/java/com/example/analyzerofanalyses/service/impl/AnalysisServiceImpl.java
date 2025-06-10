package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.service.AnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Override
    public Analysis getById(Long id) {
        return null;
    }

    @Override
    public List<Analysis> getAllByUserId(Long id) {
        return List.of();
    }

    @Override
    public Analysis update(Analysis analysis) {
        return null;
    }

    @Override
    public Analysis create(Analysis analysis, Long userId) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
