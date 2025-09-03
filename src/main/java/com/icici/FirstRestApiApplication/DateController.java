package com.icici.FirstRestApiApplication;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DateController {
    @GetMapping("/Date")
    public LocalTime ShowDate() {
        return LocalTime.now();
    }
    
}
