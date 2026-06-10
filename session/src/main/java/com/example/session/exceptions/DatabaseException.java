package com.example.session.exceptions;

public class DatabaseException extends ApplicationException {

    public DatabaseException(String message) {
        super(message, 500);
    }
}