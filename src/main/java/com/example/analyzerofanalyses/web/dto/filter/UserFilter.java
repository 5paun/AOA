package com.example.analyzerofanalyses.web.dto.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserFilter {

    private String nameOrSurname;
    private String email;
    private LocalDate dateOfBirthFrom;
    private LocalDate dateOfBirthTo;
    private Integer ageFrom;
    private Integer ageTo;

}
