package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User updated(User user) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public boolean isAnalysisOwner(Long userId, Long analysisId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
