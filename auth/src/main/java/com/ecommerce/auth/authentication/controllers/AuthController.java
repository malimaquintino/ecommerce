package com.ecommerce.auth.authentication.controllers;

import com.ecommerce.auth.authentication.dto.LoginDto;
import com.ecommerce.auth.authentication.dto.RegisterDTO;
import com.ecommerce.auth.authentication.dto.TokenResponseDTO;
import com.ecommerce.auth.authentication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        TokenResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok("Registered");
    }
}
