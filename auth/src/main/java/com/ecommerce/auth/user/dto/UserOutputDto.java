package com.ecommerce.auth.user.dto;

import com.ecommerce.auth.user.enums.UserType;
import com.ecommerce.auth.user.models.User;
import com.ecommerce.auth.user.utils.UserUtils;

import java.util.Objects;

public class UserOutputDto {
    private Long id;
    private String document;
    private String name;
    private String email;
    private UserType type;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserOutputDto that = (UserOutputDto) o;
        return enabled == that.enabled && Objects.equals(id, that.id) && Objects.equals(document, that.document) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, document, name, email, type, enabled);
    }

    @Override
    public String toString() {
        return "UserOutputDto{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", enabled=" + enabled +
                '}';
    }

    public static UserOutputDto parseFromEntity(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(user.getId());
        userOutputDto.setDocument(UserUtils.formatDocument(user.getDocument()));
        userOutputDto.setName(user.getName());
        userOutputDto.setEmail(user.getEmail());
        userOutputDto.setType(user.getType());
        userOutputDto.setEnabled(user.isEnabled());

        return userOutputDto;
    }
}
