package quintino.malima.ecommerce.identity.application.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quintino.malima.ecommerce.identity.application.AuthService;
import quintino.malima.ecommerce.identity.application.JwtService;
import quintino.malima.ecommerce.identity.application.UserService;
import quintino.malima.ecommerce.identity.application.dto.LoginRequest;
import quintino.malima.ecommerce.identity.application.dto.LoginResponse;
import quintino.malima.ecommerce.identity.domain.LoginAttempt;
import quintino.malima.ecommerce.identity.domain.User;
import quintino.malima.ecommerce.identity.infrastructure.persistence.LoginAttemptRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final LoginAttemptRepository loginAttemptRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final long employeeExpiration;

    public AuthServiceImpl(
            UserService userService,
            LoginAttemptRepository loginAttemptRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            @Value("${jwt.expiration.employee}") long employeeExpiration) {
        this.userService            = userService;
        this.loginAttemptRepository = loginAttemptRepository;
        this.passwordEncoder        = passwordEncoder;
        this.jwtService             = jwtService;
        this.employeeExpiration     = employeeExpiration;
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request, String ipAddress) {
        User user = userService.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        boolean success = passwordEncoder.matches(request.password(), user.getPassword());

        loginAttemptRepository.save(new LoginAttempt(user.getId(), success, ipAddress));

        if (!success) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getEmail(),
                employeeExpiration
        );

        return new LoginResponse(token, "Bearer", employeeExpiration);
    }
}
