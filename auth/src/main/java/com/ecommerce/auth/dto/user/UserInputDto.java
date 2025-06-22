package com.ecommerce.auth.dto.user;

import com.ecommerce.auth.enums.UserType;

import java.util.Objects;

public class UserInputDto {
    private String document;
    private String name;
    private String email;
    private String password;
    private UserType type;

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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserInputDto that = (UserInputDto) o;
        return Objects.equals(document, that.document) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, name, email, password, type);
    }

    @Override
    public String toString() {
        return "UserInputDto{" +
                "document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
