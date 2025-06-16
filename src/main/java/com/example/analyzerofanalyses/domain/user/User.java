package com.example.analyzerofanalyses.domain.user;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class User implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Symptom> symptoms;
    private List<Analysis> analyses;

}
