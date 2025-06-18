package com.example.analyzerofanalyses.domain.analysis;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AnalysisImage {

    private MultipartFile file;

}
