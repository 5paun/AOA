package com.example.analyzerofanalyses.domain.symptom;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Symptom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private String recommendation;

    @Column(name = "image")
    @CollectionTable(name = "symptom_image")
    @ElementCollection
    private List<String> images;
}
