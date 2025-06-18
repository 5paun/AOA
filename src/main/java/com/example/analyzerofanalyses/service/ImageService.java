package com.example.analyzerofanalyses.service;

import com.example.analyzerofanalyses.domain.analysis.AnalysisImage;
import com.example.analyzerofanalyses.domain.symptom.SymptomImage;

public interface ImageService {
    // нужно сделать так, чтобы работало с любым типом картинки
    String upload(SymptomImage image);

    String upload(AnalysisImage image);
}
