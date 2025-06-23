package com.ecommerce.auth.services.user;

import com.ecommerce.auth.dto.user.ChangePasswordDTO;
import com.ecommerce.auth.dto.user.UserInputDto;
import com.ecommerce.auth.dto.user.UserOutputDto;

public interface UserService {
    UserOutputDto createCustomer(UserInputDto inputDto);
    UserOutputDto updateCustomerData(Long id, UserInputDto inputDto);
    UserOutputDto changePassword(Long id, ChangePasswordDTO passwordDTO);
    void findPaginated();
}
