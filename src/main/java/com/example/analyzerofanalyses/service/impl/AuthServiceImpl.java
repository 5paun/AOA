package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.service.AuthService;
import com.example.analyzerofanalyses.web.dto.auth.JwtRequest;
import com.example.analyzerofanalyses.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}

