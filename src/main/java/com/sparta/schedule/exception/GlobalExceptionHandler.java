package com.sparta.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionMarker> handleException(IllegalArgumentException ex) {
        ExceptionMarker exceptionMarker = new ExceptionMarker(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                exceptionMarker,
                HttpStatus.BAD_REQUEST
        );
    }
}