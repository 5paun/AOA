package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    @Query(value = """
        SELECT * FROM analyses a
        JOIN users_analyses us ON ua.analysis_id = a.id
        WHERE us.user_ud = :userId 
    """, nativeQuery = true)
    List<Analysis> findAllByUserId(Long userId);

}
