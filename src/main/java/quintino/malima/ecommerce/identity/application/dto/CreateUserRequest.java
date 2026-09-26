package quintino.malima.ecommerce.identity.application.dto;

public record CreateUserRequest(
        String nome,
        String documento,
        String email,
        String senha
) {}
