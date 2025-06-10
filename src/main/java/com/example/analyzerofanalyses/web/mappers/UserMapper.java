package com.example.analyzerofanalyses.web.mappers;

import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.web.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
