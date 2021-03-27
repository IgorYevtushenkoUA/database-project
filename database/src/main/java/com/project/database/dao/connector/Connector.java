package com.project.database.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String URL = "jdbc:postgresql://localhost:5433/gulash_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "admin";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5433/gulash_db?user=postgres&password=admin");
    }
}