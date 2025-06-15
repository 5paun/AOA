package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void update(User user);

    void create(User user);

    void insertUserRole(Long userId, Role role);

    boolean isSymptomOwner(Long userId, Long symptomId);

    boolean isAnalysisOwner(Long userId, Long analysisId);

    void delete(Long id);

}
