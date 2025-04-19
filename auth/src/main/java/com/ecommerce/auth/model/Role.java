package com.ecommerce.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private UUID id;
    private String name;
    private String description;
}