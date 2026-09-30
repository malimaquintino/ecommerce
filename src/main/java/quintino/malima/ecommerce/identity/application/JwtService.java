package quintino.malima.ecommerce.identity.application;

public interface JwtService {

    String generateToken(Long userId, String email, String subject, long expirationSeconds);

    Long extractUserId(String token);

    String extractEmail(String token);

    boolean isTokenValid(String token);
}
