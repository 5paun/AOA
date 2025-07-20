package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.user.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification extends BaseSpecification {

    public static Specification<User> hasName(String value) {
        return hasStringField(value, "name");
    }

    public static Specification<User> hasEmail(String value) {
        return hasStringField(value, "email");
    }

}
