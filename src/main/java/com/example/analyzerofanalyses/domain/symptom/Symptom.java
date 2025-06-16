package com.example.analyzerofanalyses.domain.symptom;

import lombok.Data;

import java.io.Serializable;

@Data
public class Symptom implements Serializable {
    private long id;
    private String title;
    private String description;
    private String recommendation;
}
