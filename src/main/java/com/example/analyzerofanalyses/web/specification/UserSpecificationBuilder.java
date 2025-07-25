package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.web.dto.filter.UserFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationBuilder implements SpecificationBuilder<UserFilter, User>, UserSpecification {

    @Override
    public Specification<User> getSpecification(UserFilter searchRequest) {
        return hasNameOrSurname(searchRequest.getNameOrSurname())
                .and(hasEmail(searchRequest.getEmail()))
                .and(dateOfBirthFrom(searchRequest.getDateOfBirthFrom()))
                .and(dateOfBirthTo(searchRequest.getDateOfBirthTo()))
                .and(hasAgeBetween(searchRequest.getAgeFrom(), searchRequest.getAgeTo()));
    }

}
