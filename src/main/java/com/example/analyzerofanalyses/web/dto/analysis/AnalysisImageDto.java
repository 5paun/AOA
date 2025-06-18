package com.example.analyzerofanalyses.web.dto.analysis;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AnalysisImageDto {

    @NotNull(message = "Image must be not null.")
    private MultipartFile file;

}
