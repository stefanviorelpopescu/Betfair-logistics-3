package com.betfair.logistics.controller;

import com.betfair.logistics.exception.InvalidDateForOrderSearchException;
import com.betfair.logistics.exception.InvalidDestinationDtoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateForOrderSearchException.class)
    public ResponseEntity<String> handleException(InvalidDateForOrderSearchException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDestinationDtoException.class)
    public ResponseEntity<String> handleException(InvalidDestinationDtoException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

}
