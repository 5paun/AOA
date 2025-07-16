package com.example.analyzerofanalyses.web.dto.filter;

import lombok.Data;

@Data
public class SymptomFilter {

    private String title;
    private String description;
    private String recommendation;
    private Boolean hasImage;

}
