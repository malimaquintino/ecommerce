package quintino.malima.ecommerce.identity.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import quintino.malima.ecommerce.identity.application.AuthService;
import quintino.malima.ecommerce.identity.application.dto.LoginRequest;
import quintino.malima.ecommerce.identity.application.dto.LoginResponse;

@RestController
@RequestMapping("/identity/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        return authService.login(request, httpRequest.getRemoteAddr());
    }
}
