package com.example.analyzerofanalyses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class AnalyzerOfAnalysesApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AnalyzerOfAnalysesApplication.class, args);
    }

}
