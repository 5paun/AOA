package com.example.analyzerofanalyses.domain.user;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_role", joinColumns = @JoinColumn(name = "client_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "client_symptom", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "symptom_id"))
    private List<Symptom> symptoms;

    @OneToMany(fetch = FetchType.EAGER)
    //    @JoinColumn(name = "id")
    //    @JoinTable(inverseJoinColumns = @JoinColumn(name = "analysis_id"))
    // @todo (избавиться от таблицы client_analysis)
    @JoinTable(name = "client_analysis", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "analysis_id"))
    private List<Analysis> analyses;

}
