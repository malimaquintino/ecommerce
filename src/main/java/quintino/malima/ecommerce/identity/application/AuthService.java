package quintino.malima.ecommerce.identity.application;

import quintino.malima.ecommerce.identity.application.dto.LoginRequest;
import quintino.malima.ecommerce.identity.application.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request, String ipAddress);
}
