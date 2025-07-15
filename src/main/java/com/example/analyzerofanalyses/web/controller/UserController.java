package com.example.analyzerofanalyses.web.controller;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.symptom.Symptom;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.service.AnalysisService;
import com.example.analyzerofanalyses.service.SymptomService;
import com.example.analyzerofanalyses.service.UserService;
import com.example.analyzerofanalyses.web.dto.analysis.AnalysisDto;
import com.example.analyzerofanalyses.web.dto.symptom.SymptomDto;
import com.example.analyzerofanalyses.web.dto.user.UserDto;
import com.example.analyzerofanalyses.web.dto.validation.OnCreate;
import com.example.analyzerofanalyses.web.dto.validation.OnUpdate;
import com.example.analyzerofanalyses.web.mappers.AnalysisMapper;
import com.example.analyzerofanalyses.web.mappers.SymptomMapper;
import com.example.analyzerofanalyses.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User controller", description = "User API")
public class UserController {
    private final UserService userService;
    private final SymptomService symptomService;
    private final AnalysisService analysisService;

    private final UserMapper userMapper;
    private final SymptomMapper symptomMapper;
    private final AnalysisMapper analysisMapper;

    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public UserDto update(
            @Validated(OnUpdate.class) @RequestBody final UserDto dto
    ) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);

        return userMapper.toDto(updatedUser);
    }

    @PatchMapping
    @Operation(summary = "Partially update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public UserDto partialUpdate(@RequestBody final UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.partialUpdate(user);

        return userMapper.toDto(updatedUser);
    }

    @GetMapping
    @Operation(summary = "Get all UserDtos")
    @PreAuthorize("@customSecurityExpression.canAccessAllUsers()")
    public List<UserDto> getAll() {
        List<User> users = userService.getAll();

        return userMapper.toDto(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get UserDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable final Long id) {
        User user = userService.getById(id);

        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable final Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/symptoms")
    @Operation(summary = "Get all User symptoms")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<SymptomDto> getSymptomsByUserId(@PathVariable final Long id) {
        List<Symptom> symptoms = symptomService.getAllByUserId(id);

        return symptomMapper.toDto(symptoms);
    }

    @PostMapping("/{id}/symptoms/{symptomId}")
    @Operation(summary = "Add symptom to user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public Symptom assignSymptom(
            @PathVariable final Long id,
            @PathVariable final Long symptomId
    ) {
        return symptomService.assignSymptomToUser(symptomId, id);
    }

    @DeleteMapping("/{id}/symptoms/{symptomId}")
    @Operation(summary = "Delete symptom from user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void unassignSymptom(
            @PathVariable final Long id,
            @PathVariable final Long symptomId
    ) {
        symptomService.unassignSymptomFromUser(symptomId, id);
    }

    @GetMapping("/{id}/analyses")
    @Operation(summary = "Get all User analyses")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<AnalysisDto> getAnalysesByUserId(@PathVariable final Long id) {
        List<Analysis> analyses = analysisService.getAllByUserId(id);

        return analysisMapper.toDto(analyses);
    }

    @PostMapping("/{id}/analyses")
    @Operation(summary = "Add analysis to user")
    @PreAuthorize("@customSecurityExpression.canCreateAnalysis(#id)")
    public AnalysisDto createAnalysis(
            @PathVariable final Long id,
            @Validated(OnCreate.class) @RequestBody final AnalysisDto dto
    ) {
        Analysis analysis = analysisMapper.toEntity(dto);
        Analysis createdAnalysis = analysisService.create(analysis, id);

        return analysisMapper.toDto(createdAnalysis);
    }
}
