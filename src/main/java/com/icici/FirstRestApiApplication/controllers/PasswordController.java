package com.icici.FirstRestApiApplication.controllers;

import org.springframework.web.bind.annotation.*;

import com.icici.FirstRestApiApplication.entities.PasswordEntry;
import com.icici.FirstRestApiApplication.services.PasswordService;

import lombok.Data;

@Data
@RestController
@CrossOrigin
public class PasswordController {
    private final PasswordService passwordService;

    // Create or Update password (with encryption)
    @PostMapping("/passwords")
    public PasswordEntry createOrUpdatePassword(@RequestBody PasswordEntry passwordEntry) {
        return passwordService.savePassword(passwordEntry);
    }

    // Get password (with decryption)
    @GetMapping("/passwords/{id}")
    public String getDecryptedPassword(@PathVariable Long id) {
        return passwordService.getDecryptedPassword(id);
    }

    // Get all without revealing passwords
    @GetMapping("/passwords")
    public java.util.List<PasswordEntry> getAllPasswords() {
        return passwordService.getAllPasswords();
    }

    @DeleteMapping("/passwords/{id}")
    public void deletePassword(@PathVariable Long id) {
        passwordService.getPasswordRepository().deleteById(id);
    }
}
