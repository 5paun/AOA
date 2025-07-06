package com.example.analyzerofanalyses.web.security.expression;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.service.AnalysisService;
import com.example.analyzerofanalyses.service.SymptomService;
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
    private final SymptomService symptomService;
    private final AnalysisService analysisService;

    public Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public Long getUserIdByToken() {
        Authentication authentication = getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();

        return user.getId();
    }

    public boolean canAccessUser(final Long id) {
        Authentication authentication = getAuthentication();

        Long userId = getUserIdByToken();

        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN, Role.ROLE_DOCTOR);
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

    public boolean canAccessSymptom() {
        Authentication authentication = getAuthentication();

        return hasAnyRole(authentication, Role.ROLE_ADMIN, Role.ROLE_DOCTOR);
    }

    public boolean canAccessAnalysis(final Long analysisId) {
        Long userId = getUserIdByToken();
        Analysis analysis = analysisService.getById(analysisId);
        Authentication authentication = getAuthentication();

        return userService.isAnalysisOwner(userId, analysis.getId()) || hasAnyRole(authentication, Role.ROLE_ADMIN, Role.ROLE_DOCTOR);
    }
}
