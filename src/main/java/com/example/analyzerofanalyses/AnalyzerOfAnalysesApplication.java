package com.example.analyzerofanalyses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AnalyzerOfAnalysesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyzerOfAnalysesApplication.class, args);
    }

}
