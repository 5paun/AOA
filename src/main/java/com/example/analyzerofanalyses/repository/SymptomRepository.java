package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    @Query(value = """
        SELECT * FROM symptoms s
        JOIN users_symptoms us ON us.symptom_id = s.id
        WHERE us.user_ud = :userId 
    """, nativeQuery = true)
    List<Symptom> findAllByUserId(@Param("userId") Long userId);

}
