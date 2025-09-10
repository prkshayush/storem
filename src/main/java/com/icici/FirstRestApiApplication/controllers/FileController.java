package com.icici.FirstRestApiApplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.icici.FirstRestApiApplication.entities.StoredFile;
import com.icici.FirstRestApiApplication.repos.FileRepository;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    FileRepository fileRepository;

    @PostMapping("/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFiles(@RequestBody List<StoredFile> files) {
        fileRepository.saveAll(files);
    }

    @GetMapping("/files")
    public List<StoredFile> fetchAllFiles() {
        return fileRepository.findAll();
    }

    @GetMapping("/files/{id}")
    public StoredFile fetchAFile(@PathVariable("id") Long id) {
        return fileRepository.findById(id).orElse(null);
    }

}
