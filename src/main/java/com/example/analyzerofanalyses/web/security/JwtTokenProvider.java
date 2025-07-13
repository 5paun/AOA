package com.example.analyzerofanalyses.web.security;

import com.example.analyzerofanalyses.domain.exception.AccessDeniedException;
import com.example.analyzerofanalyses.domain.user.Role;
import com.example.analyzerofanalyses.domain.user.User;
import com.example.analyzerofanalyses.service.UserService;
import com.example.analyzerofanalyses.service.props.JwtProperties;
import com.example.analyzerofanalyses.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(
            final Long userId,
            final String email,
            final Set<Role> roles
    ) {
        Claims claims = Jwts.claims().subject(email)
                .add("id", userId)
                .add("roles", resolveRoles((roles)))
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(final Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .toList();
    }

    public String createRefreshToken(final Long userId, final String email) {
        Claims claims = Jwts.claims().subject(email)
                .add("id", userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.DAYS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(final String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();

        if (!isValid(refreshToken)) {
            throw new AccessDeniedException();
        }

        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setAccessToken(
                createAccessToken(userId, user.getEmail(), user.getRoles())
        );
        jwtResponse.setRefreshToken(
                createRefreshToken(userId, user.getEmail())
        );

        return jwtResponse;
    }

    public boolean isValid(final String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

        return claims.getPayload().getExpiration().after(new Date());
    }

    private String getId(final String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id")
                .toString();
    }

    private String getUsername(final String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(final String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
    }
}
