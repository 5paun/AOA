package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    @Query(value = """
                SELECT * FROM analysis a
                JOIN client_analysis ca ON ca.analysis_id = a.id
                WHERE ca.client_id = :userId
            """, nativeQuery = true)
    List<Analysis> findAllByUserId(Long userId);

}
