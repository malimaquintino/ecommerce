package quintino.malima.ecommerce.identity.application.dto;

public record CreateEmployeeRequest(
        String name,
        String document,
        String email,
        String password
) {}
