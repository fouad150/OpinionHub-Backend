package com.youcode.opinionhub.exception;

public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException(String message) {
        super(message);
    }
}
