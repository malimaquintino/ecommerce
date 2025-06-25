package com.ecommerce.auth.authentication.dto;

import java.util.Objects;

public class RegisterDTO {
    private String document;
    private String name;
    private String email;
    private String password;

    public RegisterDTO() {
    }

    public RegisterDTO(String document, String name, String email, String password) {
        this.document = document;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RegisterDTO that = (RegisterDTO) o;
        return Objects.equals(document, that.document) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, name, email, password);
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
