package com.example.session.DTO;

import java.time.LocalDateTime;

public class ErrorResponseDTO {

    private int statusCode;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(
            int statusCode,
            String error,
            String message) {

        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}