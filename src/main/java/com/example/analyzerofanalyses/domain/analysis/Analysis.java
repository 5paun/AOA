package com.example.analyzerofanalyses.domain.analysis;

import com.example.analyzerofanalyses.domain.user.User;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Analysis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private double totalCholesterol;
    private double whiteBloodCells;
    private int lymphocytes;
    private LocalDateTime createdDate;

    @Column(name = "image")
    @CollectionTable(name = "analysis_image")
    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User user;
}
