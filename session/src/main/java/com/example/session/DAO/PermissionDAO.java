package com.example.session.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.session.exceptions.DatabaseException;
import com.example.session.util.dbConnection;

public class PermissionDAO {

    public List<String> getPermissionsByUserId(int userId)
            throws DatabaseException {

        List<String> permissions = new ArrayList<>();

        String query =
                "SELECT p.permission_key " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.id " +
                "JOIN role_permissions rp ON r.id = rp.role_id " +
                "JOIN permissions p ON rp.permission_id = p.id " +
                "WHERE u.id = ?";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                permissions.add(
                        rs.getString("permission_key")
                );
            }

            return permissions;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to fetch user permissions"
            );
        }
    }

    public boolean hasPermission(int userId, String permissionKey)
            throws DatabaseException {

        String query =
                "SELECT COUNT(*) " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.id " +
                "JOIN role_permissions rp ON r.id = rp.role_id " +
                "JOIN permissions p ON rp.permission_id = p.id " +
                "WHERE u.id = ? AND p.permission_key = ?";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setString(2, permissionKey);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to validate user permission"
            );
        }
    }
}