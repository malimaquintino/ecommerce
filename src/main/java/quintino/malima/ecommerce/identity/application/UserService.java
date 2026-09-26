package quintino.malima.ecommerce.identity.application;

import quintino.malima.ecommerce.identity.application.dto.CreateUserRequest;
import quintino.malima.ecommerce.identity.application.dto.CreateUserResponse;

public interface UserService {

    CreateUserResponse create(CreateUserRequest request);
}
