package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public abstract class BaseDAOImpl {

    protected static Connection connection;
    protected final Connector connector;
    protected PreparedStatement preparedStatement;


    @Autowired
    public BaseDAOImpl(Connector connector) {
        this.connector = connector;
    }


    @PostConstruct
    void initConnection() {
        try {
            connection = connector.getConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
