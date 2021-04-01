package com.project.database.dao.connector;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public interface Connector {

    Connection getConnection() throws SQLException;
}