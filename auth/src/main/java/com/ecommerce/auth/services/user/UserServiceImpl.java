package com.ecommerce.auth.services.user;

import com.ecommerce.auth.dto.user.UserInputDto;
import com.ecommerce.auth.dto.user.UserOutputDto;
import com.ecommerce.auth.enums.UserType;
import com.ecommerce.auth.models.User;
import com.ecommerce.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserOutputDto createCustomer(UserInputDto inputDto) {
        try {
            inputDto.setType(UserType.CUSTOMER);
            String cryptPass = "todo";
            User newUser = User.parseFromUserInputDto(inputDto, cryptPass);
            return UserOutputDto.parseFromEntity(userRepository.save(newUser));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UserOutputDto updateCustomer(Long id, UserInputDto inputDto) {
        return null;
    }

    @Override
    public void findPaginated() {

    }
}
