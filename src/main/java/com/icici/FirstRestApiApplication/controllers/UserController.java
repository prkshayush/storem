package com.icici.FirstRestApiApplication.controllers;

import com.icici.FirstRestApiApplication.repos.UserRepository;
import com.icici.FirstRestApiApplication.entities.Label;
import com.icici.FirstRestApiApplication.entities.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
            existingUser.setPasswordHash(updatedUser.getPassword());
        }
        // Add more fields as necessary

        return userRepository.save(existingUser);
    }

    @PutMapping("users/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }
    @PostMapping("/users/{id}/labels")
    public void addLabelToUser(@PathVariable("id") Long userId, @RequestBody Label label) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        user.getLabels().add(label);
        userRepository.save(user);
    }
    
}
