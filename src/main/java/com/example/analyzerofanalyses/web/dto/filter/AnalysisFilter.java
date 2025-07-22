package com.example.analyzerofanalyses.web.dto.filter;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnalysisFilter {

    private Long clientId;
    private String title;
    private BigDecimal totalCholesterolFrom;
    private BigDecimal totalCholesterolTo;
    private BigDecimal whiteBloodCellsFrom;
    private BigDecimal whiteBloodCellsTo;
    private Integer lymphocytesFrom;
    private Integer lymphocytesTo;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
    private Boolean hasImage;

}
