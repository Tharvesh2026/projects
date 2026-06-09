package com.example.session.DAO;

import java.sql.*;

import com.example.session.model.User;
import com.example.session.util.dbConnection;
import com.example.session.util.PasswordHasher;

public class UserDAO {

    public User getUser(String mail) {

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
                        rs.getInt("id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean registerUser(User user) {

        String query =
                "INSERT INTO users(name, mailId, username, password) VALUES (?, ?, ?, ?)";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUname());
            ps.setString(4, user.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateUser(String mail, String pwd) {

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
            e.printStackTrace();
        }

        return false;
    }
}