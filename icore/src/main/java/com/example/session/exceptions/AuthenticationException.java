package com.example.session.exceptions;

public class AuthenticationException extends ApplicationException {

    public AuthenticationException(String message) {
        super(message, 401);
    }
}