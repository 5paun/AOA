package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.web.dto.filter.UserFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationBuilder implements SpecificationBuilder<UserFilter, User>, UserSpecification {

    @Override
    public Specification<User> getSpecification(UserFilter searchRequest) {
        return hasName(searchRequest.getName())
                .and(hasEmail(searchRequest.getEmail()));
    }

}
