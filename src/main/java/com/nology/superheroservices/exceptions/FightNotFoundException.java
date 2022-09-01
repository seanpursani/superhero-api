package com.nology.superheroservices.exceptions;

public class FightNotFoundException extends NotFoundException {
    public FightNotFoundException(Long id, String errorMessage) {
        super(errorMessage + id);
    }
}
