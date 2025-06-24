package com.ecommerce.auth.authentication.services;

import com.ecommerce.auth.authentication.dto.LoginDto;
import com.ecommerce.auth.authentication.dto.RegisterDTO;
import com.ecommerce.auth.authentication.dto.TokenResponseDTO;

public interface AuthService {
    TokenResponseDTO login(LoginDto dto);

    void register(RegisterDTO dto);
}
