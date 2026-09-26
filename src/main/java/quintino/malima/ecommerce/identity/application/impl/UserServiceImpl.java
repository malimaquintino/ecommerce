package quintino.malima.ecommerce.identity.application.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quintino.malima.ecommerce.identity.application.UserService;
import quintino.malima.ecommerce.identity.application.dto.CreateUserRequest;
import quintino.malima.ecommerce.identity.application.dto.CreateUserResponse;
import quintino.malima.ecommerce.identity.domain.User;
import quintino.malima.ecommerce.identity.infrastructure.persistence.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserResponse create(CreateUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.name(),
                request.document(),
                request.email(),
                hashedPassword
        );

        User saved = userRepository.save(user);

        return new CreateUserResponse(
                saved.getUserCode(),
                saved.getName(),
                saved.getDocument(),
                saved.getEmail(),
                saved.getStatus(),
                saved.getCreatedAt()
        );
    }
}
