package quintino.malima.ecommerce.identity.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quintino.malima.ecommerce.identity.application.dto.CreateUserRequest;
import quintino.malima.ecommerce.identity.application.dto.CreateUserResponse;
import quintino.malima.ecommerce.identity.domain.User;
import quintino.malima.ecommerce.identity.infrastructure.persistence.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponse create(CreateUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.senha());

        User user = new User(
                request.nome(),
                request.documento(),
                request.email(),
                hashedPassword
        );

        User saved = userRepository.save(user);

        return new CreateUserResponse(
                saved.getUserCode(),
                saved.getNome(),
                saved.getDocumento(),
                saved.getEmail(),
                saved.getStatus(),
                saved.getCreatedAt()
        );
    }
}
