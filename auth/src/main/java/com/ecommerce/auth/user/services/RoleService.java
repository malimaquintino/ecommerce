package com.ecommerce.auth.user.services;

import com.ecommerce.auth.user.models.Role;

public interface RoleService {
    Role getRoleByName(String name);
}
