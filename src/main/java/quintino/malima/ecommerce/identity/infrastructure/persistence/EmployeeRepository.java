package quintino.malima.ecommerce.identity.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import quintino.malima.ecommerce.identity.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
