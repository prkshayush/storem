package com.icici.FirstRestApiApplication.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icici.FirstRestApiApplication.entities.PasswordEntry;


public interface PasswordRepository extends JpaRepository<PasswordEntry, Long> {}

