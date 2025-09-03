package com.icici.FirstRestApiApplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.icici.FirstRestApiApplication.entities.Label;
import com.icici.FirstRestApiApplication.entities.StoredFile;
import com.icici.FirstRestApiApplication.repos.LabelRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class LabelController {

    @Autowired
    LabelRepository labelRepository;

    @GetMapping("/labels")
    public List<Label> fetchAllLabels() {
        return labelRepository.findAll();
    }

    @GetMapping("/labels/{id}")
    public Label fetchLabel(@PathVariable ("id") Long id) {
        return labelRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found with id " + id));
    }
    

    @PostMapping("/labels")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLabel(@RequestBody Label label) {
       labelRepository.save(label);
    }

    @DeleteMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable("id") Long id) {
        labelRepository.deleteById(id);
    }

    @PatchMapping("/labels/{id}")
    public Label updateLabel(@PathVariable("id") Long id, @RequestBody Label updatedLabel) {
        Label existingLabel = labelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found with id " + id));

        // Update only the fields that are provided in the request
        if (updatedLabel.getName() != null) {
            existingLabel.setName(updatedLabel.getName());
        }

        return labelRepository.save(existingLabel);
    }
    @PostMapping("/labels/{id}/files")
    public void addFileToLabel(@PathVariable("id") Long id, @RequestBody StoredFile file) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found with id " + id));

        label.getStoredFiles().add(file);
        labelRepository.save(label);
        
       
    }
    
}

