package com.ecommerce.auth.user.services;

import com.ecommerce.auth.exceptions.BadRequestException;
import com.ecommerce.auth.exceptions.NotFoundException;
import com.ecommerce.auth.user.dto.ChangePasswordDTO;
import com.ecommerce.auth.user.dto.RegisterDTO;
import com.ecommerce.auth.user.dto.UserInputDto;
import com.ecommerce.auth.user.dto.UserOutputDto;
import com.ecommerce.auth.user.enums.UserType;
import com.ecommerce.auth.user.models.Role;
import com.ecommerce.auth.user.models.User;
import com.ecommerce.auth.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserOutputDto createCustomer(RegisterDTO registerDTO) {
        try {
            logger.info("Creating new customer");
            Role role = roleService.getRoleByName("CUSTOMER");
            String cryptPass = passwordEncoder.encode(registerDTO.getPassword());
            User newUser = User.parseFromRegisterDto(registerDTO, cryptPass, UserType.CUSTOMER, role);
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
            String cryptPass = passwordEncoder.encode(passwordDTO.getPassword());
            user.setPassword(cryptPass);
            return UserOutputDto.parseFromEntity(userRepository.save(user));
        } catch (Exception e) {
            logger.error("Fail change password", e);
            throw e;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void findPaginated() {

    }
}
