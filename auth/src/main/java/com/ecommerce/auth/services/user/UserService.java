package com.ecommerce.auth.services.user;

import com.ecommerce.auth.dto.user.UserInputDto;
import com.ecommerce.auth.dto.user.UserOutputDto;

public interface UserService {
    UserOutputDto create(UserInputDto inputDto);
    UserOutputDto update(Long id, UserInputDto inputDto);
    void findPaginated();
}
