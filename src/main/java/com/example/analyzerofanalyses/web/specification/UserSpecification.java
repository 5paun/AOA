package com.example.analyzerofanalyses.web.specification;

import com.example.analyzerofanalyses.domain.user.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.Month;

public interface UserSpecification extends BaseSpecification<User> {

    default Specification<User> hasNameOrSurname(String nameOrSurname) {
        return (root, query, criteriaBuilder) -> {
            if (nameOrSurname == null || nameOrSurname.trim().isEmpty()) {
                return null;
            }

            String[] nameList = nameOrSurname.trim().split("\\s+");
            String firstName = nameList[0];
            String lastName = nameList.length > 1 ? nameList[1] : "";
            String firstNamePattern = "%" + firstName.trim() + "%";
            String lastNamePattern = "%" + lastName.trim() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.and(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), firstNamePattern.toLowerCase()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), lastNamePattern.toLowerCase())
                    ),
                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), firstNamePattern.toLowerCase()),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), lastNamePattern.toLowerCase())
                    )
            );
        };

    }

    default Specification<User> hasEmail(String value) {
        return hasStringField(value, "email");
    }

    default Specification<User> dateOfBirthFrom(LocalDate dateOfBirthFrom) {
        return greaterThanOrEqual(dateOfBirthFrom, "dateOfBirth");
    }

    default Specification<User> dateOfBirthTo(LocalDate dateOfBirthTo) {
        return lessThanOrEqual(dateOfBirthTo, "dateOfBirth");
    }

    default Specification<User> hasAgeBetween(Integer ageFrom, Integer ageTo) {
        return (root, query, criteriaBuilder) -> {
            if (ageFrom == null && ageTo == null) {
                return null;
            }

            LocalDate now  = LocalDate.now();
            LocalDate minDate = ageTo != null
                    ? LocalDate.of(now.minusYears(ageTo).getYear(), Month.JANUARY, 1)
                    : null;
            LocalDate maxDate = ageFrom != null
                    ? LocalDate.of(now.minusYears(ageFrom).getYear(), Month.DECEMBER, 31)
                    : null;
            Predicate agePredicate = criteriaBuilder.conjunction();

            if (minDate != null) {
                agePredicate = criteriaBuilder.and(
                        agePredicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), minDate)
                );
            }

            if (maxDate != null) {
                agePredicate = criteriaBuilder.and(
                        agePredicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), maxDate)
                );
            }

            // alternative solution
//            Expression<Integer> ageExpression = criteriaBuilder.function(
//                    "DATE_PART", // Функция для PostgreSQL для получения возраста
//                    Integer.class,
//                    criteriaBuilder.literal("year"), // Получаем годы
//                    criteriaBuilder.function("AGE", Integer.class, root.get("dateOfBirth"))
//            );
//
//            if (ageFrom != null) {
//                agePredicate = criteriaBuilder.and(
//                        agePredicate,
//                        criteriaBuilder.greaterThanOrEqualTo(ageExpression, ageFrom)
//                );
//            }
//            if (ageTo != null) {
//                agePredicate = criteriaBuilder.and(
//                        agePredicate,
//                        criteriaBuilder.lessThanOrEqualTo(ageExpression, ageTo)
//                );
//            }

            return agePredicate;
        };
    }

}
