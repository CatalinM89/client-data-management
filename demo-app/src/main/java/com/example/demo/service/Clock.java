package com.example.demo.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Externalize the control of time so that we can have repeatable tests where we can mock the time service
 *
 * @author Catalin Moisa
 */
@Component
public class Clock {

    public LocalDate now() {
        return LocalDate.now();
    }
}
