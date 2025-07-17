package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.domain.image.Image;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.repository.AnalysisRepository;
import com.example.analyzerofanalyses.service.AnalysisService;
import com.example.analyzerofanalyses.service.ImageService;
import com.example.analyzerofanalyses.service.UserService;
import com.example.analyzerofanalyses.web.dto.filter.AnalysisFilter;
import com.example.analyzerofanalyses.web.specification.AnalysisSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
    public Analysis getById(final Long id) {
        return analysisRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Analysis not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Analysis> getAllByUserId(final Long id) {
        userService.getById(id);

        return analysisRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "AnalysisService::getById", key = "#analysis.id")
    public Analysis update(final Analysis analysis) {
        Analysis analysisDto = getById(analysis.getId());
        analysis.setUser(analysisDto.getUser());
        analysisRepository.save(analysis);

        return analysis;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Analysis> search(AnalysisFilter searchRequest) {
        Specification<Analysis> specification = AnalysisSpecification
                .belongsToUser(searchRequest.getUserId())
                .and(AnalysisSpecification.hasTitle(searchRequest.getTitle()))
                .and(AnalysisSpecification.totalCholesterolFrom(searchRequest.getTotalCholesterolFrom()))
                .and(AnalysisSpecification.totalCholesterolTo(searchRequest.getTotalCholesterolTo()))
                .and(AnalysisSpecification.whiteBloodCellsFrom(searchRequest.getWhiteBloodCellsFrom()))
                .and(AnalysisSpecification.whiteBloodCellsTo(searchRequest.getWhiteBloodCellsTo()))
                .and(AnalysisSpecification.lymphocytesFrom(searchRequest.getLymphocytesFrom()))
                .and(AnalysisSpecification.lymphocytesTo(searchRequest.getLymphocytesTo()))
                .and(AnalysisSpecification.createdDateFrom(searchRequest.getCreatedDateFrom()))
                .and(AnalysisSpecification.createdDateTo(searchRequest.getCreatedDateTo()))
                .and(AnalysisSpecification.hasImage(searchRequest.getHasImage()));

        return analysisRepository.findAll(specification);
    }

    @Override
    @Transactional
    public Analysis partialUpdate(final Analysis analysis) {
        Long id = analysis.getId();
        Analysis existingAnalysis = getById(id);

        if (analysis.getTitle() != null) {
            existingAnalysis.setTitle(analysis.getTitle());
        }

        if (analysis.getTotalCholesterol() != null) {
            existingAnalysis.setTotalCholesterol(analysis.getTotalCholesterol());
        }

        if (analysis.getWhiteBloodCells() != null) {
            existingAnalysis.setWhiteBloodCells(analysis.getWhiteBloodCells());
        }

        if (analysis.getLymphocytes() != null) {
            existingAnalysis.setLymphocytes(analysis.getLymphocytes());
        }

        if (analysis.getCreatedDate() != null) {
            existingAnalysis.setCreatedDate(analysis.getCreatedDate());
        }

        analysisRepository.save(existingAnalysis);

        return existingAnalysis;
    }


    @Override
    @Transactional
    public Analysis create(final Analysis analysis, final Long userId) {
        User user = userService.getById(userId);
        analysis.setUser(user);
        analysisRepository.save(analysis);
        user.getAnalyses().add(analysis);
        userService.update(user);

        return analysis;
    }

    @Override
    @Transactional
    @CacheEvict(value = "AnalysisService::getById", key = "#id")
    public void delete(final Long id) {
        analysisRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "AnalysisService::getById", key = "#id")
    public void uploadImage(final Long id, final Image image) {
        Analysis analysis = getById(id);
        String fileName = imageService.upload(image);
        analysis.getImages().add(fileName);
        analysisRepository.save(analysis);
    }
}
