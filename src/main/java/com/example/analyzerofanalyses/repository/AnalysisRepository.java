package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    @Query(value = """
                SELECT * FROM analysis a
                WHERE a.client_id = :userId
            """, nativeQuery = true)
    List<Analysis> findAllByUserId(Long userId);

    Page<Analysis> findAll(Specification<Analysis> specification, Pageable pageable);

}
