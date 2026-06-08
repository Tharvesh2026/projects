package com.example.session.model;

public class User {
    private final int id;
    private final String uname;
    private final String email;
    private final String password;
    private final String name;

    public User(String uname, String email, String password, String name) {
        this(uname, email, password, name, 0);
    }

    public User(String uname, String email, String password, String name, int id) {
        this.uname = uname;
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

