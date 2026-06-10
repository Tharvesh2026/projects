package com.example.session.model;

public class User {
    private final int id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final String name;
    private final String role;

    public User(String username, String email, String passwordHash,
                String name, String role) {
        this(username, email, passwordHash, name, role, 0);
    }

    public User(String username, String email, String passwordHash,
                String name, String role, int id) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getName() { return name; }
    public String getRole() { return role; }
}