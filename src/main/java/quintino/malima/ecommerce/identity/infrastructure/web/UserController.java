package quintino.malima.ecommerce.identity.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import quintino.malima.ecommerce.identity.application.UserService;
import quintino.malima.ecommerce.identity.application.dto.CreateUserRequest;
import quintino.malima.ecommerce.identity.application.dto.CreateUserResponse;

@RestController
@RequestMapping("/identity/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse create(@RequestBody CreateUserRequest request) {
        return userService.create(request);
    }
}
