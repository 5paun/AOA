package com.example.analyzerofanalyses.service;

import com.example.analyzerofanalyses.domain.user.User;

public interface UserService {
    User getById(Long id);

    User getByEmail(String email);

    User update(User user);

    User create(User user);

    boolean isSymptomOwner (Long userId, Long symptomId);

    boolean isAnalysisOwner(Long userId, Long analysisId);

    void delete(Long id);

}
