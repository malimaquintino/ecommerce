package com.ecommerce.auth.controllers;

import com.ecommerce.auth.dto.user.UserInputDto;
import com.ecommerce.auth.dto.user.UserOutputDto;
import com.ecommerce.auth.services.user.UserService;
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
    public ResponseEntity<?> create(@Valid @RequestBody UserInputDto inputDto) {
        UserOutputDto response = userService.create(inputDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody UserInputDto inputDto) {
        UserOutputDto response = userService.update(id, inputDto);
        return ResponseEntity.ok(response);
    }
}
