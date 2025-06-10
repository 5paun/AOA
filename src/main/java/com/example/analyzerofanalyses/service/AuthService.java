package com.example.analyzerofanalyses.service;

import com.example.analyzerofanalyses.web.dto.auth.JwtRequest;
import com.example.analyzerofanalyses.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
