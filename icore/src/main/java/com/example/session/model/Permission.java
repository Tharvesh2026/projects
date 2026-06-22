package com.example.session.model;
public class Permission {

    private int id;
    private String permissionKey;
    private String description;

    public Permission(
            int id,
            String permissionKey,
            String description) {

        this.id = id;
        this.permissionKey = permissionKey;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public String getDescription() {
        return description;
    }
}