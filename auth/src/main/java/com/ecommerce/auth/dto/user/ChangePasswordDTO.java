package com.ecommerce.auth.dto.user;

public class ChangePasswordDTO {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChangePasswordDTO(String password) {
        this.password = password;
    }

    public ChangePasswordDTO() {
    }
}
