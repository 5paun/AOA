package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.repository.AnalysisRepository;
import com.example.analyzerofanalyses.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class    AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;

    @Override
    @Transactional(readOnly = true)
    public Analysis getById(Long id) {
        return analysisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Analysis not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Analysis> getAllByUserId(Long id) {
        return analysisRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public Analysis update(Analysis analysis) {
        analysisRepository.update(analysis);

        return analysis;
    }

    @Override
    @Transactional
    public Analysis create(Analysis analysis, Long userId) {
        analysisRepository.create(analysis);
        analysisRepository.assignToUserById(analysis.getId(), userId);

        return analysis;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        analysisRepository.delete(id);
    }
}
