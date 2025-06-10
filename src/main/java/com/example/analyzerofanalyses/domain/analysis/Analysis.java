package com.example.analyzerofanalyses.domain.analysis;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Analysis {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
}
