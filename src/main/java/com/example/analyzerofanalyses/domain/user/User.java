package com.example.analyzerofanalyses.domain.user;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @CollectionTable(name = "users_symptoms")
    @ManyToMany
    @JoinTable(name = "symptom_Id")
    private List<Symptom> symptoms;

    @CollectionTable(name = "users_analysis")
    @OneToMany
    @JoinColumn(name = "analysis_id")
    private List<Analysis> analyses;

}
