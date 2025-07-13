package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.analysis.AnalysisImage;
import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.repository.AnalysisRepository;
import com.example.analyzerofanalyses.service.AnalysisService;
import com.example.analyzerofanalyses.service.ImageService;
import com.example.analyzerofanalyses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    @CachePut(value = "AnalysisService::getById::getById", key = "#analysis.id")
    public Analysis update(final Analysis analysis) {
        Analysis analysisDto = getById(analysis.getId());
        analysis.setUser(analysisDto.getUser());
        analysisRepository.save(analysis);

        return analysis;
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
    public void uploadImage(final Long id, final AnalysisImage image) {
        Analysis analysis = getById(id);
        String fileName = imageService.upload(image);
        analysis.getImages().add(fileName);
        analysisRepository.save(analysis);
    }
}
