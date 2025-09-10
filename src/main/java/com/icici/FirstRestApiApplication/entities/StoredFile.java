package com.icici.FirstRestApiApplication.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class StoredFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String storagePath;
    private Long fileSize;
    private LocalDateTime uploadedAt = LocalDateTime.now();

    // @ManyToOne
    // private User user;
    // @ManyToOne
    // private Label label;

    // getters/setters
}
