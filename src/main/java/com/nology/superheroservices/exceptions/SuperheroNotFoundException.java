package com.nology.superheroservices.exceptions;

public class SuperheroNotFoundException extends NotFoundException {
    public SuperheroNotFoundException(Long id, String errorMessage) {
        super(errorMessage + id);
    }
}
