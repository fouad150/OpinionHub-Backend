package com.youcode.opinionhub.exception;

public class DoesntHavePermission extends RuntimeException {
    public DoesntHavePermission(String message) {
        super(message);
    }
}
