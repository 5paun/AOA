package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.image.Image;
import com.example.analyzerofanalyses.web.dto.Image.ImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper extends Mappable<Image, ImageDto> {
}
