package quintino.malima.ecommerce.identity.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import quintino.malima.ecommerce.identity.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
