package quintino.malima.ecommerce.identity.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateEmployeeResponse(
        UUID employeeCode,
        String name,
        String document,
        String email,
        Short status,
        LocalDateTime createdAt
) {}
