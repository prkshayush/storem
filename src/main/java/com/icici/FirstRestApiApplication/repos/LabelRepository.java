package com.icici.FirstRestApiApplication.repos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.icici.FirstRestApiApplication.entities.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {}