package com.example.analyzerofanalyses.service;


import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.domain.symptom.SymptomImage;

import java.util.List;

public interface SymptomService {
    Symptom getById(Long id);

    List<Symptom> getAllByUserId(Long id);

    Symptom update(Symptom symptom);

    Symptom create(Symptom symptom, Long userId);

    void delete(Long id);

    void uploadImage(Long id, SymptomImage image);
}
