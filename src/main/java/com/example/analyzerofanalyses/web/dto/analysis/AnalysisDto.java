package com.example.analyzerofanalyses.web.dto.analysis;

import com.example.analyzerofanalyses.web.dto.validation.OnCreate;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnalysisDto {
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(
            message = "Title must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = 255,
            message = "Title length must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String title;

    private float totalCholesterol;
    private float whiteBloodCells;
    private int lymphocytes;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> images;
}
