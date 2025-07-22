package com.example.analyzerofanalyses.web.specification;


import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<R, E> extends BaseSpecification<E> {

    Specification<E> getSpecification(R r);

}
