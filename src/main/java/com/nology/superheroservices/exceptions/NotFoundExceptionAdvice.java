package com.nology.superheroservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundExceptionAdvice {
    @ResponseBody
    @ExceptionHandler({ NotFoundException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String NotSeededHandler(NotFoundException ex) {
        return ex.getMessage();
    }
}
