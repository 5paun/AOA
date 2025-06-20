package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.domain.symptom.SymptomImage;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.repository.SymptomRepository;
import com.example.analyzerofanalyses.service.ImageService;
import com.example.analyzerofanalyses.service.SymptomService;
import com.example.analyzerofanalyses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {
    private final SymptomRepository symptomRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "SymptomService::getById", key = "#id")
    public Symptom getById(final Long id) {
        return symptomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("symptom not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Symptom> getAllByUserId(final Long id) {
        return symptomRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "SymptomService::getById", key = "#symptom.id")
    public Symptom update(final Symptom symptom) {
        symptomRepository.save(symptom);

        return symptom;
    }

    @Override
    @Transactional
    @Cacheable(value = "SymptomService::getById", key = "#symptom.id")
    public Symptom create(final Symptom symptom, final Long userId) {
        User user = userService.getById(userId);
        user.getSymptoms().add(symptom);
        userService.update(user);

        return symptom;
    }

    @Override
    @Transactional
    @CacheEvict(value = "SymptomService::getById", key = "#id")
    public void delete(final Long id) {
        symptomRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "SymptomService::getById", key = "#id")
    public void uploadImage(final Long id, final SymptomImage image) {
        Symptom symptom = getById(id);
        String fileName = imageService.upload(image);
        symptom.getImages().add(fileName);
        symptomRepository.save(symptom);
    }
}
