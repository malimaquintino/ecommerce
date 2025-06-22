package com.ecommerce.auth.models;

import com.ecommerce.auth.dto.user.UserInputDto;
import com.ecommerce.auth.enums.UserType;
import com.ecommerce.auth.utils.UserUtils;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", schema = "auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType type;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //@ManyToMany(fetch = FetchType.EAGER)
    // private Set<Role> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //public Set<Role> getRoles() {
    //  return roles;
    //}

    //public void setRoles(Set<Role> roles) {
    //  this.roles = roles;
    //}


    public static User parseFromUserInputDto(UserInputDto inputDto, String crypt) {
        User user = new User();
        user.setDocument(UserUtils.cleanDocumentFormat(inputDto.getDocument()));
        user.setName(inputDto.getName());
        user.setEmail(inputDto.getEmail());
        user.setEnabled(true);
        user.setType(inputDto.getType());
        user.setPassword(crypt);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return user;
    }
}
