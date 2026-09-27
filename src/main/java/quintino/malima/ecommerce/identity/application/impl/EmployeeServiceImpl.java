package quintino.malima.ecommerce.identity.application.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quintino.malima.ecommerce.identity.application.EmployeeService;
import quintino.malima.ecommerce.identity.application.UserService;
import quintino.malima.ecommerce.identity.application.dto.CreateEmployeeRequest;
import quintino.malima.ecommerce.identity.application.dto.CreateEmployeeResponse;
import quintino.malima.ecommerce.identity.application.dto.CreateUserRequest;
import quintino.malima.ecommerce.identity.domain.Employee;
import quintino.malima.ecommerce.identity.domain.User;
import quintino.malima.ecommerce.identity.infrastructure.persistence.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.userService        = userService;
    }

    @Override
    @Transactional
    public CreateEmployeeResponse create(CreateEmployeeRequest request) {
        User savedUser = userService.create(new CreateUserRequest(
                request.name(),
                request.document(),
                request.email(),
                request.password()
        ));

        Employee employee = new Employee(savedUser.getId());
        Employee saved    = employeeRepository.save(employee);

        return new CreateEmployeeResponse(
                saved.getEmployeeCode(),
                savedUser.getName(),
                savedUser.getDocument(),
                savedUser.getEmail(),
                saved.getStatus(),
                saved.getCreatedAt()
        );
    }
}
