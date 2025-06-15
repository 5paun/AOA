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

    public boolean canAccessUser(Long id) {
        System.out.println("id " + id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication " + authentication);

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        System.out.println("user " + user);

        Long userId = user.getId();
        System.out.println("userId " + userId);
        System.out.println("userId.equals(id) " + userId.equals(id));
        System.out.println("hasAnyRole(authentication, Role.ROLE_ADMIN) " + hasAnyRole(authentication, Role.ROLE_ADMIN));

        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());

            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }

        return false;
    }

    private boolean canAccessSymptom(Long symptomId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isAnalysisOwner(userId, symptomId);
    }

    private boolean canAccessAnalysis(Long analysisId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isAnalysisOwner(userId, analysisId);
    }
}
