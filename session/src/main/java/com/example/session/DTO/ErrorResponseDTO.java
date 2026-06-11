package com.example.session.DTO;

public class ErrorResponseDTO {

    private int statusCode;
    private String message;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(
            int statusCode,
            String message) {

        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}