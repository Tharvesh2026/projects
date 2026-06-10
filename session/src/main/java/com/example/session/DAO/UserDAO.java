package com.example.session.DAO;

import java.sql.*;

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
                        rs.getInt("id")
                    );
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public boolean registerUser(User user) throws DatabaseException {

        String query = "INSERT INTO users(name, mailId, username, password, role VALUES (?, ?, ?, ?, ?)";

        try (Connection con = dbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getRole());

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
}