package com.example.session.DTO.Req;

public class RegisterRequestDTO {

    private String name;
    private String username;
    private String email;
    private String password;

    public RegisterRequestDTO() {
        // must need this or else it trow error : Cannot construct instance, bcuz Jackson first creates it object
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}