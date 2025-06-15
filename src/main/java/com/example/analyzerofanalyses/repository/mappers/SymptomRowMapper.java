package com.example.analyzerofanalyses.repository.mappers;

import com.example.analyzerofanalyses.domain.symptom.Symptom;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SymptomRowMapper {

    @SneakyThrows
    public static Symptom mapRow(ResultSet resultSet) {
        if (resultSet.next())  {
            Symptom symptom = new Symptom();
            symptom.setId(resultSet.getLong("symptom_id"));
            symptom.setTitle(resultSet.getString("symptom_title"));
            symptom.setDescription(resultSet.getString("symptom_description"));
            symptom.setRecommendation((resultSet.getString("symptom_recommendation")));

            return symptom;
        }

        return null;
    }

    @SneakyThrows
    public static List<Symptom> mapRows(ResultSet resultSet) {
        List<Symptom> symptoms = new ArrayList<>();

        while (resultSet.next())  {
            Symptom symptom = new Symptom();
            symptom.setId(resultSet.getLong("symptom_id"));

            if (!resultSet.wasNull())  {
                symptom.setTitle(resultSet.getString("symptom_title"));
                symptom.setDescription(resultSet.getString("symptom_description"));
                symptom.setRecommendation((resultSet.getString("symptom_recommendation")));
                symptoms.add(symptom);
            }
        }

        return symptoms;
    }
}
