package com.ecommerce.auth.services.user;

import com.ecommerce.auth.dto.user.UserInputDto;
import com.ecommerce.auth.dto.user.UserOutputDto;

public interface UserService {
    UserOutputDto createCustomer(UserInputDto inputDto);
    UserOutputDto updateCustomer(Long id, UserInputDto inputDto);
    void findPaginated();
}
