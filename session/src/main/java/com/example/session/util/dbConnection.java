package com.example.session.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class dbConnection {
    private static final Properties properties = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            InputStream inputStream =
                    dbConnection.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            if (inputStream == null) {
                throw new RuntimeException(
                        "config.properties file not found");
            }

            properties.load(inputStream);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found: ", e);
        }catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load database configuration",
                    e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            properties.getProperty("db.url"),
            properties.getProperty("db.user"),
            properties.getProperty("db.password"));
    }

    
}
