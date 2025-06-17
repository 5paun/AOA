package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.repository.UserRepository;
import com.example.analyzerofanalyses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByEmail", key = "#email")
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByEmail", key = "#user.email")
    })
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getById", key = "#user.id"),
            @Cacheable(value = "UserService::getByUsername", key = "#user.email")
    })
    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }

        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    @Cacheable(value = "UserService::isSymptomOwner", key = "#userId + '.' + #symptomId")
    public boolean isSymptomOwner(Long userId, Long symptomId) {
        return userRepository.isSymptomOwner(userId, symptomId);
    }

    @Override
    @Transactional
    @Cacheable(value = "UserService::isAnalysisOwner", key = "#userId + '.' + #analysisId")
    public boolean isAnalysisOwner(Long userId, Long analysisId) {
        return userRepository.isAnalysisOwner(userId, analysisId);
    }

    @Override
    @CacheEvict(value = "UserService.getById", key = "#id")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
