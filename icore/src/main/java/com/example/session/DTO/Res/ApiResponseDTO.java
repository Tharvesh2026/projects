package com.example.session.DTO.Res;

public class ApiResponseDTO {

    private boolean success;
    private String message;
    private Object data;

    public ApiResponseDTO() {
    }

    public ApiResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public ApiResponseDTO(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}