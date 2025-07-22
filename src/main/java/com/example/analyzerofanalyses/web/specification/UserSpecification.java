package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.user.User;
import org.springframework.data.jpa.domain.Specification;

public interface UserSpecification extends BaseSpecification<User> {

    default Specification<User> hasName(String value) {
        return hasStringField(value, "name");
    }

    default Specification<User> hasEmail(String value) {
        return hasStringField(value, "email");
    }

}
