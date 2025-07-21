package com.example.analyzerofanalyses.service;


import com.example.analyzerofanalyses.domain.image.Image;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.web.dto.filter.SymptomFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SymptomService {
    List<Symptom> getAll();

    Symptom getById(Long id);

    List<Symptom> getAllByUserId(Long id);

    Page<Symptom> search(SymptomFilter searchRequest, Pageable pageable);

    Symptom update(Symptom symptom);

    Symptom partialUpdate(Symptom symptom);

    Symptom create(Symptom symptom);

    void delete(Long id);

    void uploadImage(Long id, Image image);

    Symptom assignSymptomToUser(Long symptomId, Long userId);

    void unassignSymptomFromUser(Long symptomId, Long userId);
}
