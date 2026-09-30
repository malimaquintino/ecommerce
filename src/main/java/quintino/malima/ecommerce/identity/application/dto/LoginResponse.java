package quintino.malima.ecommerce.identity.application.dto;

public record LoginResponse(
        String token,
        String type,
        long expiresIn
) {}
