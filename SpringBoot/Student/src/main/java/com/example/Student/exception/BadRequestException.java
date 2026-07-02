package com.example.Student.exception;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(message, "BAD_REQUEST");
    }
}