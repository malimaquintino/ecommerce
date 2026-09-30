package quintino.malima.ecommerce.identity.application.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quintino.malima.ecommerce.identity.application.JwtService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(Long userId, String email, String subject, long expirationSeconds) {
        long nowMillis = System.currentTimeMillis();

        return Jwts.builder()
                .subject(subject)
                .claim("userId", userId)
                .claim("email", email)
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(nowMillis + expirationSeconds * 1000))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Long extractUserId(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    @Override
    public String extractEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Date expiration = parseClaims(token).getExpiration();
            return expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
