package com.example.analyzerofanalyses.web.dto.symptom;

import com.example.analyzerofanalyses.web.dto.validation.OnCreate;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class SymptomDto {
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 50, message = "Title length must be smaller than 50 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @NotNull(message = "Description must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 500, message = "Description length must be smaller than 500 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @NotNull(message = "Recommendation must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 1000, message = "Recommendation length must be smaller than 1000 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String recommendation;
}
