package com.example.analyzerofanalyses.web.mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface Mappable<E, D> {

    D toDto(E entity);

    List<D> toDto(List<E> entities);

    default Page<D> toDto(Page<E> entities) {
        List<D> dtos = toDto(entities.getContent());
        return new PageImpl<>(dtos, entities.getPageable(), entities.getTotalElements());
    }

    E toEntity(D dto);

    List<E> toEntity(List<D> dtos);
}
