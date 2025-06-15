package com.example.analyzerofanalyses.domain.analysis;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Analysis {
    private Long id;
    private String title;
    private float totalCholesterol;
    private float whiteBloodCells;
    private int lymphocytes;
    private LocalDateTime createdDate;
}
