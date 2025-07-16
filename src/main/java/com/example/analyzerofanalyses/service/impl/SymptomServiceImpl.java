package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.domain.image.Image;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.repository.SymptomRepository;
import com.example.analyzerofanalyses.service.ImageService;
import com.example.analyzerofanalyses.service.SymptomService;
import com.example.analyzerofanalyses.service.UserService;
import com.example.analyzerofanalyses.web.dto.filter.SymptomFilter;
import com.example.analyzerofanalyses.web.specification.SymptomSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Symptom> getAll() {
        return symptomRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Symptom getById(final Long id) {
        return symptomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Symptom not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Symptom> getAllByUserId(final Long id) {
        User user = userService.getById(id);

        return symptomRepository.findAllByUserId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Symptom> search(SymptomFilter searchRequest) {
        Specification<Symptom> specification = SymptomSpecification
                .hasTitle(searchRequest.getTitle())
                .and(SymptomSpecification.hasDescription(searchRequest.getDescription()))
                .and(SymptomSpecification.hasRecommendation(searchRequest.getRecommendation()))
                .and(SymptomSpecification.hasImage(searchRequest.getHasImage()));

        return symptomRepository.findAll(specification);
    }

    @Override
    @Transactional
    @CachePut(value = "SymptomService::getById", key = "#symptom.id")
    public Symptom update(final Symptom symptom) {
        getById(symptom.getId());
        symptomRepository.save(symptom);

        return symptom;
    }

    @Override
    @Transactional
    @CachePut(value = "SymptomService::getById", key = "#symptom.id")
    public Symptom partialUpdate(final Symptom symptom) {
        Long id = symptom.getId();
        Symptom existingSymptom = getById(id);

        if (symptom.getTitle() != null) {
            existingSymptom.setTitle(symptom.getTitle());
        }

        if (symptom.getDescription() != null) {
            existingSymptom.setDescription(symptom.getDescription());
        }

        if (symptom.getRecommendation() != null) {
            existingSymptom.setRecommendation(symptom.getRecommendation());
        }

        symptomRepository.save(existingSymptom);

        return existingSymptom;
    }

    @Override
    @Transactional
    public Symptom create(final Symptom symptom) {
        symptomRepository.save(symptom);

        return symptom;
    }

    @Override
    @Transactional
    @CacheEvict(value = "SymptomService::getById", key = "#id")
    public void delete(final Long id) {
        getById(id);
        symptomRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "SymptomService::getById", key = "#id")
    public void uploadImage(final Long id, final Image image) {
        Symptom symptom = getById(id);
        String fileName = imageService.upload(image);
        symptom.getImages().add(fileName);
        symptomRepository.save(symptom);
    }

    @Override
    @Transactional
    public Symptom assignSymptomToUser(final Long symptomId, final Long userId) {
        Symptom symptom = getById(symptomId);
        User user = userService.getById(userId);
        user.getSymptoms().add(symptom);

        return symptom;
    }

    @Override
    @Transactional
    public void unassignSymptomFromUser(final Long symptomId, final Long userId) {
        Symptom symptom = getById(symptomId);
        User user = userService.getById(userId);
        user.getSymptoms().remove(symptom);
    }
}
