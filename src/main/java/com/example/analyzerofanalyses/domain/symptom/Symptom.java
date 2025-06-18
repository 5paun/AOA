package com.example.analyzerofanalyses.domain.symptom;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "symptoms")
@Data
public class Symptom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String description;
    private String recommendation;

    @Column(name = "image")
    @CollectionTable(name = "symptoms_images")
    @ElementCollection
    private List<String> images;
}
