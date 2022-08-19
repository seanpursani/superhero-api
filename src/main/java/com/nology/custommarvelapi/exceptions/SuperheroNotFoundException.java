package com.nology.custommarvelapi.exceptions;

public class SuperheroNotFoundException extends Exception {
    public SuperheroNotFoundException(Long id, String errorMessage) {
        super(errorMessage + id);
    }
}
