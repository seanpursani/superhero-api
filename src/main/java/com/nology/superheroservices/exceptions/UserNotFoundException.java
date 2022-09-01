package com.nology.superheroservices.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id, String errorMessage) {
        super(errorMessage + id);
    }
}

