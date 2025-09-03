package com.icici.FirstRestApiApplication.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icici.FirstRestApiApplication.entities.StoredFile;

public interface FileRepository extends JpaRepository<StoredFile, Long> {}


