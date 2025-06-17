package com.example.analyzerofanalyses.domain.symptom;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

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
}
