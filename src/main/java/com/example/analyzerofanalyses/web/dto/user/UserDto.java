package com.example.analyzerofanalyses.web.dto.user;

import com.example.analyzerofanalyses.web.dto.validation.OnCreate;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User id ", example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User first name ", example = "Denis")
    @NotNull(
            message = "First name must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = 255,
            message = "First name length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String firstName;

    @Schema(description = "User last name ", example = "Shalashov")
    @NotNull(
            message = "Last name must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = 255,
            message = "Last name length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String lastName;

    @Schema(description = "User email ", example = "test@mail.ru")
    @NotNull(
            message = "Email must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = 255,
            message = "Email length must be smaller than 255 symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String email;

    @Schema(
            description = "User crypted password ",
            example = "12345"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(
            message = "Password must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String password;

    @Schema(
            description = "User password confirmation ",
            example = "12345"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(
            message = "Password confirmation must be not null.",
            groups = {OnCreate.class}
    )
    private String passwordConfirmation;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Integer age;
}
