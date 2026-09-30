package quintino.malima.ecommerce.identity.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import quintino.malima.ecommerce.identity.domain.LoginAttempt;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
}
