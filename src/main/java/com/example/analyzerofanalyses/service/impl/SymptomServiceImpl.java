package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.repository.SymptomRepository;
import com.example.analyzerofanalyses.service.SymptomService;
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

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "SymptomService::getById", key = "#id")
    public Symptom getById(Long id) {
        return symptomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("symptom not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Symptom> getAllByUserId(Long id) {
        return symptomRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "SymptomService::getById", key = "#symptom.id")
    public Symptom update(Symptom symptom) {
        symptomRepository.update(symptom);

        return symptom;
    }

    @Override
    @Transactional
    @Cacheable(value = "SymptomService::getById", key = "#symptom.id")
    public Symptom create(Symptom symptom, Long userId) {
        symptomRepository.create(symptom);
        symptomRepository.assignToUserById(symptom.getId(), userId);

        return symptom;
    }

    @Override
    @Transactional
    @CacheEvict(value = "SymptomService::getById", key = "#id")
    public void delete(Long id) {
        symptomRepository.delete(id);
    }
}
