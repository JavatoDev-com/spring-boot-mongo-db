package com.javatodev.api.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String document) {
        super("Related " + document + " not found.");
    }
}
