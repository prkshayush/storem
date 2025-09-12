package com.icici.FirstRestApiApplication.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PasswordEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String username;
    // @JsonIgnore
    private String password;
    // private LocalDateTime createdAt = LocalDateTime.now();

    // @ManyToOne
    // private User user;

    // getters/setters
}