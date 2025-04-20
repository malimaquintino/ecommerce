package com.ecommerce.auth.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private Long id;
    private String name;
    private String description;
}