package com.ecommerce.auth.jwt.services;

import com.ecommerce.auth.user.models.Role;
import com.ecommerce.auth.user.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRoles().stream()
                        .map(Role::getName).toList())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusHours(2)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractSubject(String token) {
        return parseToken(token).getBody().getSubject();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    public List<String> extractRoles(String token) {
        Claims claims = parseToken(token).getBody();
        Object roles = claims.get("roles");

        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }

        return Collections.emptyList();
    }

}
