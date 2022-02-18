package com.javatodev.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map> handleNotfoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(prepareResponse(exception.getMessage()));
    }

    @ExceptionHandler(InvalidMemberStatusException.class)
    public ResponseEntity<Map> handleInvalidMemberStatusException(InvalidMemberStatusException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(prepareResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(prepareResponse(exception.getMessage()));
    }

    // Response message can be detailed here
    private Map<String, String> prepareResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
