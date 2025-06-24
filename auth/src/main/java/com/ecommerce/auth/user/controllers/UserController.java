package com.ecommerce.auth.user.controllers;

import com.ecommerce.auth.user.dto.ChangePasswordDTO;
import com.ecommerce.auth.user.dto.UserInputDto;
import com.ecommerce.auth.user.dto.UserOutputDto;
import com.ecommerce.auth.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody UserInputDto inputDto) {
        UserOutputDto response = userService.createCustomer(inputDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@PathVariable long id, @Valid @RequestBody UserInputDto inputDto) {
        UserOutputDto response = userService.updateCustomerData(id, inputDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/change-password/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomerPassword(@PathVariable long id, @Valid @RequestBody ChangePasswordDTO passwordDto) {
        UserOutputDto response = userService.changePassword(id, passwordDto);
        return ResponseEntity.ok(response);
    }
}
