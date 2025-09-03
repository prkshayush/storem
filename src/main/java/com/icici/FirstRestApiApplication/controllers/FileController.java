package com.icici.FirstRestApiApplication.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @PostMapping("/upload")
    public String uploadFile() {
        return "Upload file API";
    }

    @GetMapping
    public String getFiles() {
        return "Get all files API";
    }
}
