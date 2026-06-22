package com.example.session.DTO.Req;

public class ResetPasswordRequestDTO {

    private int id;
    private String newPassword;

    public ResetPasswordRequestDTO() {
    }

    public int getId() {
        return id;
    }

    public String getNewPassword() {
        return newPassword;
    }
}