package quintino.malima.ecommerce.identity.application;

import quintino.malima.ecommerce.identity.application.dto.CreateEmployeeRequest;
import quintino.malima.ecommerce.identity.application.dto.CreateEmployeeResponse;

public interface EmployeeService {

    CreateEmployeeResponse create(CreateEmployeeRequest request);
}
