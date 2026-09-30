package quintino.malima.ecommerce.identity.application;

import quintino.malima.ecommerce.identity.application.dto.CreateUserRequest;
import quintino.malima.ecommerce.identity.domain.User;

import java.util.Optional;

public interface UserService {

    User create(CreateUserRequest request);

    Optional<User> findByEmail(String email);
}
