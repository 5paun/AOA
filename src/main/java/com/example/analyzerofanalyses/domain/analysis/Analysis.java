package com.example.analyzerofanalyses.domain.analysis;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "analysis")
@Data
public class Analysis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private double totalCholesterol;
    private double whiteBloodCells;
    private int lymphocytes;
    private LocalDateTime createdDate;

    @Column(name = "image")
    @CollectionTable(name = "analyses_images")
    @ElementCollection
    private List<String> images;
}
