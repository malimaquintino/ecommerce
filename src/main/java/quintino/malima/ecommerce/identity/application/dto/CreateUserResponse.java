package quintino.malima.ecommerce.identity.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateUserResponse(
        UUID userCode,
        String nome,
        String documento,
        String email,
        String status,
        LocalDateTime createdAt
) {}
