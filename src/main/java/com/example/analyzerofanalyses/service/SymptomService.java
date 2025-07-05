package com.example.analyzerofanalyses.service;


import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.domain.symptom.SymptomImage;

import java.util.List;

public interface SymptomService {
    List<Symptom> getAll();

    Symptom getById(Long id);

    List<Symptom> getAllByUserId(Long id);

    Symptom update(Symptom symptom);

    Symptom create(Symptom symptom);

    void delete(Long id);

    void uploadImage(Long id, SymptomImage image);

    Symptom assignSymptomToUser(Long symptomId, Long userId);

    void unassignSymptomFromUser(Long symptomId, Long userId);
}
