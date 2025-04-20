package com.ecommerce.auth.dto.user;

import com.ecommerce.auth.enums.UserType;

import java.util.Objects;

public class UserOutputDto {
    private String name;
    private String email;
    private UserType type;
    private boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOutputDto that = (UserOutputDto) o;
        return enabled == that.enabled && Objects.equals(name, that.name) && Objects.equals(email, that.email) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, type, enabled);
    }

    @Override
    public String toString() {
        return "UserOutputDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", enabled=" + enabled +
                '}';
    }
}
