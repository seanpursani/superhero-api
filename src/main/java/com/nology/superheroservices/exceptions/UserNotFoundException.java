package com.nology.superheroservices.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long id, String errorMessage) {
        super(errorMessage + id);
    }
}

