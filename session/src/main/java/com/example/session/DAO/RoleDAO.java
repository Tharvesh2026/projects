package com.example.session.DAO;

import com.example.session.model.Role;
import com.example.session.util.dbConnection;
import com.example.session.exceptions.DatabaseException;

import java.sql.*;
import java.util.*;

public class RoleDAO {
    public List<Role> getAllRoles() throws DatabaseException {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT id, role_name, status FROM roles ORDER BY role_name";
        try (
                Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Role role = new Role(
                        rs.getInt("id"),
                        rs.getString("role_name"),
                        rs.getString("status"));
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to fetch roles");
        }
    }

    public Role getRoleById(int id)
            throws DatabaseException {

        String query = "SELECT id, role_name, status " +
                "FROM roles " +
                "WHERE id = ?";

        try (
                Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)
            ) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return new Role(
                            rs.getInt("id"),
                            rs.getString("role_name"),
                            rs.getString("status"));
                }
                return null;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to fetch role");
        }
    }

    public boolean createRole(String roleName)
            throws DatabaseException {

        String query = "INSERT INTO roles(role_name) VALUES(?)";

        try (
                Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, roleName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to create role");
        }
    }
public boolean updateRoleStatus(int roleId, String status)
        throws DatabaseException {

    String query =
            "UPDATE roles SET status = ? WHERE id = ?";

    try (
            Connection con = dbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
    ) {
        ps.setString(1, status);
        ps.setInt(2, roleId);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        throw new DatabaseException(
                "Unable to update role status"
        );
    }
}
}
