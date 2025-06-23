package com.ecommerce.auth.services.user;

import com.ecommerce.auth.dto.user.ChangePasswordDTO;
import com.ecommerce.auth.dto.user.UserInputDto;
import com.ecommerce.auth.dto.user.UserOutputDto;
import com.ecommerce.auth.enums.UserType;
import com.ecommerce.auth.exceptions.BadRequestException;
import com.ecommerce.auth.exceptions.NotFoundException;
import com.ecommerce.auth.models.User;
import com.ecommerce.auth.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserOutputDto createCustomer(UserInputDto inputDto) {
        try {
            logger.info("Creating new customer");
            inputDto.setType(UserType.CUSTOMER);
            String cryptPass = "todo";
            User newUser = User.parseFromUserInputDto(inputDto, cryptPass);
            return UserOutputDto.parseFromEntity(userRepository.save(newUser));
        } catch (Exception e) {
            logger.error("Fail to create new customer", e);
            throw e;
        }
    }

    @Override
    public UserOutputDto updateCustomerData(Long id, UserInputDto inputDto) {
        try {
            logger.info("Updating customer");
            inputDto.setType(UserType.CUSTOMER);

            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                throw new NotFoundException("Customer not found");
            }

            User userToUpdate = user.updateUserFromInputDto(inputDto);
            return UserOutputDto.parseFromEntity(userRepository.save(userToUpdate));

        } catch (Exception e) {
            logger.error("Fail to update customer", e);
            throw e;
        }
    }

    @Override
    public UserOutputDto changePassword(Long id, ChangePasswordDTO passwordDTO) {
        try {
            logger.info("Changing Password");

            if (passwordDTO.getPassword().isBlank()) {
                throw new BadRequestException("Insert valid password");
            }

            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                throw new NotFoundException("User not found");
            }
            user.setPassword(passwordDTO.getPassword());
            return UserOutputDto.parseFromEntity(userRepository.save(user));
        } catch (Exception e) {
            logger.error("Fail change password", e);
            throw e;
        }
    }

    @Override
    public void findPaginated() {

    }
}
