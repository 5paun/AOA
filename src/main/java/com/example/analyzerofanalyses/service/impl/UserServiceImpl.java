package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.exception.ResourceNotFoundException;
import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.repository.UserRepository;
import com.example.analyzerofanalyses.service.UserService;
import com.example.analyzerofanalyses.web.dto.filter.UserFilter;
import com.example.analyzerofanalyses.web.specification.UserSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSpecificationBuilder userSpecificationBuilder;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> search(UserFilter searchRequest, Pageable pageable) {
        Specification<User> specification = userSpecificationBuilder.getSpecification(searchRequest);

        return userRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByEmail", key = "#user.email")
    })
    public User update(final User user) {
        Long userId = user.getId();
        getById(userId);

        if (isEmailExist(user.getEmail(), userId)) {
            throw new IllegalStateException("Email already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public User partialUpdate(final User user) {
        Long userId = user.getId();
        User existingUser = getById(userId);

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            if (isEmailExist(user.getEmail(), userId)) {
                throw new IllegalStateException("Email already exists.");
            }

            existingUser.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);

        return existingUser;
    }


    @Override
    public User create(final User user) {
        if (isEmailExist(user.getEmail(), user.getId())) {
            throw new IllegalStateException("Email already exists.");
        }

        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException(
                    "Password and password confirmation do not match."
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public boolean isSymptomOwner(final Long userId, final Long symptomId) {
        return userRepository.isSymptomOwner(userId, symptomId);
    }

    @Override
    @Transactional
    public boolean isAnalysisOwner(final Long userId, final Long analysisId) {
        return userRepository.isAnalysisOwner(userId, analysisId);
    }

    @Override
    @CacheEvict(value = "UserService.getById", key = "#id")
    public void delete(final Long id) {
        getById(id);
        userRepository.deleteById(id);
    }

    private boolean isEmailExist(final String email, final Long id) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent() && !user.get().getId().equals(id);
    }
}
