package com.nology.superheroservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SuperheroNotFoundExceptionAdvice {
        @ResponseBody
        @ExceptionHandler(SuperheroNotFoundException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        String SuperheroNotSeededHandler (SuperheroNotFoundException ex) {
            return ex.getMessage();
        }
}

