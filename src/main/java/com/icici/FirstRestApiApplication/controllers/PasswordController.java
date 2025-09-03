package com.icici.FirstRestApiApplication.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passwords")
public class PasswordController {

    @PostMapping
    public String addPassword() {
        return "Add password API";
    }

    @GetMapping
    public String getPasswords() {
        return "Get all passwords API";
    }
}


