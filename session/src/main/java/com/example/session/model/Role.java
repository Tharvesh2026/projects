package com.example.session.model;
public class Role {

    private int id;
    private String roleName;
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Role(
            int id,
            String roleName,String status) {

        this.id = id;
        this.roleName = roleName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}