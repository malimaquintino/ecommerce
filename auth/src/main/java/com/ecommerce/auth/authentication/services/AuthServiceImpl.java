package com.ecommerce.auth.authentication.services;

import com.ecommerce.auth.authentication.dto.LoginDto;
import com.ecommerce.auth.authentication.dto.RegisterDTO;
import com.ecommerce.auth.authentication.dto.TokenResponseDTO;
import com.ecommerce.auth.exceptions.NotFoundException;
import com.ecommerce.auth.exceptions.UnauthorizedException;
import com.ecommerce.auth.jwt.services.JwtService;
import com.ecommerce.auth.user.dto.UserInputDto;
import com.ecommerce.auth.user.models.User;
import com.ecommerce.auth.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenResponseDTO login(LoginDto dto) {
        User user = userService.findUserByEmail(dto.getEmail());

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new TokenResponseDTO(token);
    }

    @Override
    public void register(RegisterDTO dto) {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setDocument(dto.getCpf());
        userInputDto.setName(dto.getName());
        userInputDto.setEmail(dto.getEmail());
        userInputDto.setPassword(dto.getPassword());
        userService.createCustomer(userInputDto);
    }
}
