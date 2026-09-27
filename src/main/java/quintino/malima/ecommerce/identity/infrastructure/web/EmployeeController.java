package quintino.malima.ecommerce.identity.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import quintino.malima.ecommerce.identity.application.EmployeeService;
import quintino.malima.ecommerce.identity.application.dto.CreateEmployeeRequest;
import quintino.malima.ecommerce.identity.application.dto.CreateEmployeeResponse;

@RestController
@RequestMapping("/identity/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEmployeeResponse create(@RequestBody CreateEmployeeRequest request) {
        return employeeService.create(request);
    }
}
