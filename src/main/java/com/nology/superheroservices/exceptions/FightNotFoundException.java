package com.nology.superheroservices.exceptions;

public class FightNotFoundException extends Exception {
    public FightNotFoundException(Long id, String errorMessage) {
        super(errorMessage + id);
    }
}
