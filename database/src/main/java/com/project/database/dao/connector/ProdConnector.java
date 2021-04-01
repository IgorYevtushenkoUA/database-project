package com.project.database.dao.connector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Profile("prod")
public class ProdConnector implements Connector {


    private final String connectionUrl;


    public ProdConnector(@Value("${connection.url}") String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }


    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(connectionUrl);
    }
}
