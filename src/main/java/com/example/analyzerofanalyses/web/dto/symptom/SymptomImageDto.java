package com.example.analyzerofanalyses.web.dto.symptom;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SymptomImageDto {

    @NotNull(message = "Image must be not null.")
    private MultipartFile file;

}
