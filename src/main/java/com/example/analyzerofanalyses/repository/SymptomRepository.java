package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.symptom.Symptom;

import java.util.List;
import java.util.Optional;

public interface SymptomRepository {
    Optional<Symptom> findById(Long id);

    List<Symptom> findAllByUserId(Long userId);

    void assignToUserById(Long symptomId, Long userId);

    void update(Symptom symptom);

    void create(Symptom symptom);

    void delete(Long id);
}
