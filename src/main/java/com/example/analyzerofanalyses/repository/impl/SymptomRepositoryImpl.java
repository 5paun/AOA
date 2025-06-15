package com.example.analyzerofanalyses.repository.impl;

import com.example.analyzerofanalyses.domain.exception.ResourceMappingException;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.repository.DataSourceConfig;
import com.example.analyzerofanalyses.repository.SymptomRepository;
import com.example.analyzerofanalyses.repository.mappers.SymptomRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SymptomRepositoryImpl implements SymptomRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT s.id as symptom_id,
                   s.title as symptom_title,
                   s.description as symptom_description,
                   s.recommendation as symptom_recommendation
            FROM symptoms s
            WHERE id = ?""";

    private final String FIND_ALL_BY_USER_ID = """
            SELECT s.id as symptom_id,
                   s.title as symptom_title,
                   s.description as symptom_description,
                   s.recommendation as symptom_recommendation
            FROM symptoms s
            JOIN users_symptoms us on s.id = us.symptom_id
            WHERE us.user_id = ?""";

    private final String ASSIGN = """
            INSERT INTO users_symptoms (symptom_id, user_id)
            VALUES(?, ?)""";

    private final String UPDATE = """
            UPDATE symptoms
            SET title = ?,
                description = ?,
                recommendation = ?
                WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO symptoms (title, description, recommendation)
            VALUES (?, ?, ?);""";

    private final String DELETE = """
            DELETE FROM symptoms
            WHERE id = ?""";

    @Override
    public Optional<Symptom> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(SymptomRowMapper.mapRow(resultSet));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding user by id");
        }
    }

    @Override
    public List<Symptom> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return SymptomRowMapper.mapRows(resultSet);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while finding all by user id");
        }
    }

    @Override
    public void assignToUserById(Long symptomId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, symptomId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while assigning to user");
        }
    }

    @Override
    public void update(Symptom symptom) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, symptom.getTitle());

            if (symptom.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, symptom.getDescription());
            }

            if (symptom.getRecommendation() == null) {
                statement.setNull(3, Types.VARCHAR);
            } else {
                statement.setString(3, symptom.getRecommendation());
            }

            statement.setLong(4, symptom.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while updating symptom");
        }
    }

    @Override
    public void create(Symptom symptom) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, symptom.getTitle());

            if (symptom.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, symptom.getDescription());
            }

            if (symptom.getRecommendation() == null) {
                statement.setNull(3, Types.VARCHAR);
            } else {
                statement.setString(3, symptom.getRecommendation());
            }

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                symptom.setId(resultSet.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while creating symptom");
        }

    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while deleting symptom");
        }
    }
}
