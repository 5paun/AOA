package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.user.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    private UserSpecification() {}

    public static Specification<User> hasName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> email == null ? null : criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

}
