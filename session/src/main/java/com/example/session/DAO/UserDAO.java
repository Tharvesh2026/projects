package com.example.session.DAO;
import java.sql.*;

import com.example.session.model.User;
import com.example.session.util.dbConnection;


public class UserDAO {
    public User getUser(String mail, String pwd){
        try(Connection con = dbConnection.getConnection()){
            Statement db = con.createStatement();
            String query = "SELECT * FROM users WHERE mailID = '" + mail + "' AND password = '" + pwd + "'";
            ResultSet rs = db.executeQuery(query);

            if(rs.next()){
                return new User(rs.getString("username"), rs.getString("mailID"),rs.getString("password"),
                rs.getString("name"), rs.getInt("id"));
            }
        } catch (SQLException e) {
        //    system.err.println("Error fetching user: " + e.toString());
           e.printStackTrace();
        }
        return null;
    }

    public Boolean validateUser(String mail, String pwd){
        try(Connection con = dbConnection.getConnection()){
            Statement db = con.createStatement();
            String query = "SELECT * FROM users WHERE mailID = '" + mail + "' AND password = '" + pwd + "'";
            ResultSet rs = db.executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            // system.err.println("Error validating user: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }
}
