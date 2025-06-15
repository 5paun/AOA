package com.example.analyzerofanalyses.repository.impl;

import com.example.analyzerofanalyses.domain.exception.ResourceMappingException;
import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.repository.DataSourceConfig;
import com.example.analyzerofanalyses.repository.UserRepository;
import com.example.analyzerofanalyses.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.email as user_email,
                   u.password as user_password,
                   ur.role as user_role_role,
                   s.id as symptom_id,
                   s.title as symptom_title,
                   s.description as symptom_description,
                   s.recommendation as symptom_recommendation
            FROM users u
                LEFT JOIN users_roles ur on u.id = ur.user_id
                LEFT JOIN users_symptoms us on u.id = us.user_id
                LEFT JOIN symptoms s on us.symptom_id = s.id
            WHERE u.id = ?
            """;

    private final String FIND_BY_EMAIL = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.email as user_email,
                   u.password as user_password,
                   ur.role as user_role_role,
                   s.id as symptom_id,
                   s.title as symptom_title,
                   s.description as symptom_description,
                   s.recommendation as symptom_recommendation
            FROM users u
                LEFT JOIN users_roles ur on u.id = ur.user_id
                LEFT JOIN users_symptoms us on u.id = us.user_id
                LEFT JOIN symptoms s on us.symptom_id = s.id
            WHERE u.email = ?
            """;

    private final String UPDATE = """
            UPDATE users\s
            SET name = ?,
                email = ?,
                password = ?
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO users (name, email, password)
            VALUES (?, ?, ?)""";

    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?, ?)""";

    private final String IS_SYMPTOM_OWNER = """
            SELECT exists (
                SELECT 1 from users_symptoms
                     WHERE user_id = ?
                     AND symptom_id = ?
            )""";

    private final String IS_ANALYSIS_OWNER = """
            SELECT exists (
                SELECT 1 from users_analysis
                     WHERE user_id = ?
                     AND analysis_id = ?
            )""";

    private final String DELETE = """
            DELETE FROM users
            WHERE id = ?""";

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by id.");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by username.");
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            // statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while updating user.");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
           statement.executeUpdate();

           try (ResultSet resultSet = statement.getGeneratedKeys()) {
               resultSet.next();
               user.setId(resultSet.getLong(1));
           }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while creating user.");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while inserting user role.");
        }
    }

    @Override
    public boolean isSymptomOwner(Long userId, Long symptomId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_SYMPTOM_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, symptomId);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                return resultSet.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while checking if user is symptom owner.");
        }
    }

    @Override
    public boolean isAnalysisOwner(Long userId, Long analysisId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_ANALYSIS_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, analysisId);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                return resultSet.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while checking if user is analysis owner.");
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
            throw new ResourceMappingException("Exception while deleting user.");
        }
    }
}
