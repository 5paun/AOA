package com.example.analyzerofanalyses.domain.analysis;

import jakarta.persistence.*;
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
    private float totalCholesterol;
    private float whiteBloodCells;
    private int lymphocytes;
    private LocalDateTime createdDate;

    @Column(name = "image")
    @CollectionTable(name = "analyses_images")
    @ElementCollection
    private List<String> images;
}
