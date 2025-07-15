package com.example.analyzerofanalyses.service;

import com.example.analyzerofanalyses.domain.user.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(Long id);

    User getByEmail(String email);

    User update(User user);

    User partialUpdate(User user);

    User create(User user);

    boolean isSymptomOwner(Long userId, Long symptomId);

    boolean isAnalysisOwner(Long userId, Long analysisId);

    void delete(Long id);

}
