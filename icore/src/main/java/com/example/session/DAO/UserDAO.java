package com.example.session.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.dbConnection;
import com.example.session.util.PasswordHasher;

public class UserDAO {

    public User getUser(String mail) throws DatabaseException {

        String query = "SELECT u.id, u.name, u.mailId, u.username, u.password, u.status, r.role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.mailId = ?";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, mail);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("mailId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role_name"),
                        rs.getString("status"),
                        rs.getInt("id"));
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public boolean registerUser(User user) throws DatabaseException {

        String query = "INSERT INTO users(name, mailId, username, password, role_id, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPasswordHash());
            ps.setInt(5, 1);
            ps.setString(6, user.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public boolean validateUser(String mail, String pwd) throws DatabaseException {

        String query = "SELECT password FROM users WHERE mailId = ?";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, mail);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                return PasswordHasher.verify(pwd, storedHash);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Database operation failed");
        }

        return false;
    }

    public List<User> getAllUsers() throws DatabaseException {

        List<User> users = new ArrayList<>();

        String query = "SELECT u.id, u.name, u.mailId, u.username, u.password, u.status, r.role_name FROM users u JOIN roles r ON u.role_id = r.id ORDER BY u.id";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                User user = new User(
                        rs.getString("username"),
                        rs.getString("mailId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role_name"),
                        rs.getString("status"),
                        rs.getInt("id"));

                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to fetch users");
        }
    }

    public User getUserById(int id) throws DatabaseException {
        String query = "SELECT u.id, u.name, u.mailId, u.username, u.password, u.status, r.role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("mailId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role_name"),
                        rs.getString("status"),
                        rs.getInt("id"));
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Unable to fetch user by id");
        }
    }

    public boolean updateUserRole(int id, int roleId)
            throws DatabaseException {

        String query = "UPDATE users " +
                "SET role_id = ? " +
                "WHERE id = ?";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, roleId);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Unable to update user role");
        }
    }

    public boolean updateUserStatus(int id, String status) throws DatabaseException {

        String query = "UPDATE users SET status = ? WHERE id = ?";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Unable to update user status");
        }
    }

    public boolean resetPassword(int id, String hashedPassword) throws DatabaseException {

        String query = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, hashedPassword);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Unable to reset password");
        }
    }
}