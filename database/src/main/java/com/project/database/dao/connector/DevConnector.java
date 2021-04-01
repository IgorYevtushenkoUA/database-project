package com.project.database.dao.connector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Profile("dev")
public class DevConnector implements Connector {


    private final String connectionUrl;


    public DevConnector(@Value("${spring.datasource.url}") String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }


    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(connectionUrl);
    }
}
