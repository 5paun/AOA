package com.example.analyzerofanalyses.service;

import com.example.analyzerofanalyses.domain.user.User;

public interface UserService {
    User getById(Long id);

    User getByUsername(String username);

    User updated(User user);

    User create(User user);

    boolean isAnalysisOwner(Long userId, Long analysisId);

    void delete(Long id);

}
