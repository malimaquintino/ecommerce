package quintino.malima.ecommerce.identity.application;

import quintino.malima.ecommerce.identity.domain.User;
import quintino.malima.ecommerce.identity.application.dto.CreateUserRequest;

public interface UserService {

    User create(CreateUserRequest request);
}
