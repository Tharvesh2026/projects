package com.example.session.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.session.model.Permission;

import com.example.session.exceptions.DatabaseException;
import com.example.session.util.dbConnection;

public class PermissionDAO {
    public List<Permission> getAllPermissions()
            throws DatabaseException {

        List<Permission> permissions = new ArrayList<>();

        String query = "SELECT id, permission_key, description " +
                "FROM permissions " +
                "ORDER BY permission_key";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                permissions.add(
                        new Permission(
                                rs.getInt("id"),
                                rs.getString("permission_key"),
                                rs.getString("description")));
            }

            return permissions;

        } catch (SQLException e) {
            throw new DatabaseException("Unable to fetch permissions");
        }
    }

    public List<String> getPermissionsByUserId(int userId)
            throws DatabaseException {

        List<String> permissions = new ArrayList<>();

        String query = "SELECT p.permission_key " +
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
                        rs.getString("permission_key"));
            }

            return permissions;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to fetch user permissions");
        }
    }

    public List<Integer> getPermissionIdsByRoleId(int roleId)
            throws DatabaseException {

        List<Integer> permissionIds = new ArrayList<>();

        String query = "SELECT permission_id " +
                "FROM role_permissions " +
                "WHERE role_id = ?";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, roleId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                permissionIds.add(rs.getInt("permission_id"));
            }

            return permissionIds;

        } catch (SQLException e) {
            throw new DatabaseException("Unable to fetch role permissions");
        }
    }

    public boolean hasPermission(int userId, String permissionKey)
            throws DatabaseException {

        String query = "SELECT COUNT(*) " +
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
                    "Unable to validate user permission");
        }
    }

    public boolean updateRolePermissions(
            int roleId,
            String[] permissionIds)
            throws DatabaseException {

        String deleteQuery = "DELETE FROM role_permissions WHERE role_id = ?";

        String insertQuery = "INSERT INTO role_permissions(role_id, permission_id) VALUES (?, ?)";

        try (
                Connection con = dbConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                    PreparedStatement deletePs = con.prepareStatement(deleteQuery)) {

                deletePs.setInt(1, roleId);
                deletePs.executeUpdate();
            }

            if (permissionIds != null) {

                try (
                        PreparedStatement insertPs = con.prepareStatement(insertQuery)) {

                    for (String permissionId : permissionIds) {

                        insertPs.setInt(1, roleId);
                        insertPs.setInt(2, Integer.parseInt(permissionId));

                        insertPs.addBatch();
                    }

                    insertPs.executeBatch();
                }
            }

            con.commit();

            return true;

        } catch (Exception e) {
            throw new DatabaseException(
                    "Unable to update role permissions");
        }
    }
}