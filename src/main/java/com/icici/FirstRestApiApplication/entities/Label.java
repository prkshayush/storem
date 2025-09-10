package com.icici.FirstRestApiApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    List<StoredFile> storedFiles;

    // getters/setters
}
