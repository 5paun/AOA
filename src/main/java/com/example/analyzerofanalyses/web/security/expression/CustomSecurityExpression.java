package com.example.analyzerofanalyses.web.security.expression;

import com.example.analyzerofanalyses.domain.analysis.Analysis;
import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.service.AnalysisService;
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

    public boolean isUserAuthorized(final Long id) {
        Long userId = getUserIdByToken();

        return userId.equals(id);
    }

    public boolean canAccessUser(final Long id) {
        Authentication authentication = getAuthentication();

        return isUserAuthorized(id) || hasAnyRole(authentication, Role.ROLE_ADMIN, Role.ROLE_DOCTOR);
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

    public boolean canCUDAnalysis(final Long analysisId) {
        Analysis analysis = analysisService.getById(analysisId);
        Long userId = getUserIdByToken();

        return userService.isAnalysisOwner(userId, analysis.getId());
    }

    public boolean canAccessAnalysis(final Long analysisId) {
        Authentication authentication = getAuthentication();


        return canCUDAnalysis(analysisId) || hasAnyRole(authentication, Role.ROLE_ADMIN, Role.ROLE_DOCTOR);
    }
}
