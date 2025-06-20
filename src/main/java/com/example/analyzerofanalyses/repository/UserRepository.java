package com.example.analyzerofanalyses.repository;

import com.example.analyzerofanalyses.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = """
             SELECT exists (
                    SELECT 1
                    FROM users_symptoms
                    WHERE user_id = :userId
                    AND symptom_id = :symptomId
                )
            """, nativeQuery = true)
    boolean isSymptomOwner(
            @Param("userId") Long userId,
            @Param("symptomId") Long symptomId
    );

    @Query(value = """
                SELECT exists (
                        SELECT 1
                        FROM users_analysis
                        WHERE user_id = :userId
                        AND analysis_id = :analysisId
                    )
            """, nativeQuery = true)
    boolean isAnalysisOwner(
            @Param("userId") Long userId,
            @Param("analysisId") Long analysisId
    );

}
