package com.example.session.DTO.Res;

import java.time.LocalDateTime;

public class ErrorResponseDTO {

    private int statusCode;
    private String error;
    private String message;
    private String timestamp;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(int statusCode, String error, String message) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
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

    public String getTimestamp() {
        return timestamp;
    }
}