package com.example.session.DTO.Res;

public class ProfileResponseDTO {

    private int id;
    private String name;
    private String username;
    private String email;
    private String role;
    private String status;

    public ProfileResponseDTO() {
    }

    public ProfileResponseDTO(int id, String name, String username,
                              String email, String role, String status) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
}