package quintino.malima.ecommerce.identity.application.dto;

public record LoginRequest(
        String email,
        String password
) {}
