package com.example.analyzerofanalyses.domain.user;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Analysis> analyses;

}
