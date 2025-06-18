package com.example.analyzerofanalyses.domain.symptom;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SymptomImage {

    private MultipartFile file;

}
