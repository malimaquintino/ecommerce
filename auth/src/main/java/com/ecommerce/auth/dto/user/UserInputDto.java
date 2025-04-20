package com.ecommerce.auth.dto.user;

import com.ecommerce.auth.enums.UserType;

import java.util.Objects;

public class UserInputDto {
    private String name;
    private String email;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        UserInputDto userInput = (UserInputDto) o;
        return enabled == userInput.enabled && Objects.equals(name, userInput.name) && Objects.equals(email, userInput.email) && Objects.equals(password, userInput.password) && type == userInput.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, type, enabled);
    }

    @Override
    public String toString() {
        return "UserInput{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", enabled=" + enabled +
                '}';
    }
}
