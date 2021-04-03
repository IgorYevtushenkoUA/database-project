package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.connector.ProdConnector;
import com.project.database.dao.inter.GroupDao;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class GroupDaoImpl implements GroupDao {

    private static Connection connection;
    private PreparedStatement preparedStatement;

    private final Connector connector;
    private int index = 1;

    public GroupDaoImpl(){
        this.connector = new ProdConnector("jdbc:postgresql://localhost:5433/gulash_db?user=postgres&password=admin");
    }

    @PostConstruct
    void initConnection() {
        try {
            connection = connector.getConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    @Override
    public void deleteByID(int groupId) {
        index = 1;
        try {
            String sql = "delete from group g where g.group_code = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
