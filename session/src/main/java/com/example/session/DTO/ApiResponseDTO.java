package com.example.session.DTO;

public class ApiResponseDTO {

    private boolean success;
    private String message;

    public ApiResponseDTO() {
    }

    public ApiResponseDTO(
            boolean success,
            String message) {

        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}