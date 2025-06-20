package com.example.analyzerofanalyses.web.security.expression;

import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.service.UserService;
import com.example.analyzerofanalyses.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean canAccessUser(final Long id) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(
            final Authentication authentication,
            final Role... roles
    ) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                    role.name()
            );

            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }

        return false;
    }

    public boolean canAccessSymptom(final Long symptomId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isSymptomOwner(userId, symptomId);
    }

    public boolean canAccessAnalysis(final Long analysisId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isAnalysisOwner(userId, analysisId);
    }
}
