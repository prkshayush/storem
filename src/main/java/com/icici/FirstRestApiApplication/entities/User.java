package com.icici.FirstRestApiApplication.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String username;
    private String email;
    private String password; // store hashed password
    private LocalDateTime created = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    List<Label> labels;

    @OneToMany(cascade = CascadeType.ALL)
    List<StoredFile> storedFiles;

    @OneToMany(cascade = CascadeType.ALL)
    List<PasswordEntry> passwordEntries;

}
