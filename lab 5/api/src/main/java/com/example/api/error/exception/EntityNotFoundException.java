package com.example.api.error.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Could not find entity");
    }
}
