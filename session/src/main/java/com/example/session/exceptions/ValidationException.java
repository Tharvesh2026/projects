package com.example.session.exceptions;

public class ValidationException extends ApplicationException {

    public ValidationException(String message) {
        super(message, 400);
    }
}