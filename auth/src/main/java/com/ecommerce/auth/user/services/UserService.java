package com.ecommerce.auth.user.services;

import com.ecommerce.auth.user.dto.ChangePasswordDTO;
import com.ecommerce.auth.user.dto.UserInputDto;
import com.ecommerce.auth.user.dto.UserOutputDto;
import com.ecommerce.auth.user.models.User;

public interface UserService {
    UserOutputDto createCustomer(UserInputDto inputDto);
    UserOutputDto updateCustomerData(Long id, UserInputDto inputDto);
    UserOutputDto changePassword(Long id, ChangePasswordDTO passwordDTO);
    User findUserByEmail(String email);
    void findPaginated();
}
