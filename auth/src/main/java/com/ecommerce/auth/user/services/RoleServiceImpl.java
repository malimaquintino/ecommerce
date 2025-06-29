package com.ecommerce.auth.user.services;

import com.ecommerce.auth.exceptions.BadRequestException;
import com.ecommerce.auth.user.models.Role;
import com.ecommerce.auth.user.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Role name is empty");
        }
        return roleRepository.findByName(name).orElseThrow();
    }
}
