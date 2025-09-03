package com.icici.FirstRestApiApplication.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String username;
    private String email;
    private String password;  // store hashed password
    private LocalDateTime created = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    List<Label> labels;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    public void setPasswordHash(String password) {
        this.password = password;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    public List<Label> getLabels() {
        return labels;
    }
    public void setLabels(List<Label> labels) {
        this.labels = labels;
    };

    

}
