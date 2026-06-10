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

        String query = "SELECT * FROM users WHERE mailId = ?";

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
                        rs.getString("role"),
                        rs.getString("status"),
                        rs.getInt("id"));
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public boolean registerUser(User user) throws DatabaseException {

        String query = "INSERT INTO users(name, mailId, username, password, role, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getRole());
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

        String query = "SELECT * FROM users ORDER BY id";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                User user = new User(
                        rs.getString("username"),
                        rs.getString("mailId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role"),
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
}