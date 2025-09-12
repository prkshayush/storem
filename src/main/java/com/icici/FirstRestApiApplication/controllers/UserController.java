package com.icici.FirstRestApiApplication.controllers;

import com.icici.FirstRestApiApplication.repos.UserRepository;
import com.icici.FirstRestApiApplication.services.PasswordService;
import com.icici.FirstRestApiApplication.entities.Label;
import com.icici.FirstRestApiApplication.entities.PasswordEntry;
import com.icici.FirstRestApiApplication.entities.StoredFile;
import com.icici.FirstRestApiApplication.entities.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @GetMapping("/users/search")
    public User getUserByName(@RequestParam("name") String name) {
        return userRepository.findByUsername(name);

    }

    // GET /users
    @GetMapping("/users")
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User fetchAUser(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));
    }

    //
    // POST /users
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED) // 👈 tells Spring to return 201
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // DELETE /users/{id}
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 👈 204 if success
    public void deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @PatchMapping("/users/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));

        // Update only the fields that are provided in the request
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        // Add more fields as necessary

        return userRepository.save(existingUser);
    }

    @PostMapping("/users/{id}/labels")
    public void addLabelToUser(@PathVariable("id") Long userId, @RequestBody Label label) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        user.getLabels().add(label);
        userRepository.save(user);
    }

    @GetMapping("/users/{id}/labels")
    public List<Label> getUserLabels(@PathVariable("id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        return user.getLabels();
    }

    @GetMapping("/users/{id}/labels/{labelId}/files")
    public List getFilesInUserLabel(@PathVariable("id") Long userId, @PathVariable("labelId") Long labelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        Label label = user.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Label not found with id " + labelId + " for user with id " + userId));

        return label.getStoredFiles();
    }

    @GetMapping("/users/{id}/files")
    public List<StoredFile> fetchFilesFromUserId(@PathVariable("id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        return user.getLabels().stream()
                .flatMap(label -> label.getStoredFiles().stream())
                .toList();
    }

    @GetMapping("/users/{id}/passwords")
public List<PasswordEntry> fetchPasswordById(@PathVariable("id") Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

    return user.getPasswordEntries().stream()
            .toList();
}

@GetMapping("/users/{id}/passwords/{passwordId}")
public PasswordEntry fetchApasswordById(
        @PathVariable("id") Long userId,
        @PathVariable("passwordId") Long passwordId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id " + userId));

    PasswordEntry passwordEntry = user.getPasswordEntries().stream()
            .filter(p -> p.getId().equals(passwordId))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Password entry not found with id " + passwordId + " for user with id " + userId));

    // decrypt the password before returning
    String decryptedPassword = passwordService.getDecryptedPassword(passwordEntry.getId());
    passwordEntry.setPassword(decryptedPassword);

    return passwordEntry;
}

    @PostMapping("/users/{id}/passwords")
public PasswordEntry addPasswordToUser(@PathVariable("id") Long userId, @RequestBody PasswordEntry passwordEntry) {
    User user = userRepository.findById(userId)
            .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

    // Encrypt before saving
    PasswordEntry encryptedEntry = passwordService.savePassword(passwordEntry);

    user.getPasswordEntries().add(encryptedEntry);
    userRepository.save(user);

    return encryptedEntry;
}


    @DeleteMapping("/users/{id}/passwords/{passwordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePasswordFromUser(@PathVariable("id") Long userId, @PathVariable("passwordId") Long passwordId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        PasswordEntry passwordEntry = user.getPasswordEntries().stream()
                .filter(p -> p.getId().equals(passwordId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Password entry not found with id " + passwordId + " for user with id " + userId));

        user.getPasswordEntries().remove(passwordEntry);
        userRepository.save(user);
    }
}
