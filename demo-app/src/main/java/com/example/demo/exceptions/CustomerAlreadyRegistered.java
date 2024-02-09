package com.example.demo.exceptions;

/**
 * @author Catalin Moisa
 */
public class CustomerAlreadyRegistered extends RuntimeException {

    public CustomerAlreadyRegistered(String message) {
        super(message);
    }
}
