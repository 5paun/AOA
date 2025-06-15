package com.example.analyzerofanalyses.domain.symptom;

import lombok.Data;

@Data
public class Symptom {
    private long id;
    private String title;
    private String description;
    private String recommendation;
}
