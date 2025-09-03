package com.icici.FirstRestApiApplication.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icici.FirstRestApiApplication.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}