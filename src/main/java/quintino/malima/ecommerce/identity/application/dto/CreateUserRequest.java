package quintino.malima.ecommerce.identity.application.dto;

public record CreateUserRequest(
        String name,
        String document,
        String email,
        String password
) {}
