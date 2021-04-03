package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.connector.ProdConnector;
import com.project.database.dao.inter.TutorDao;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TutorDaoImpl implements TutorDao {

    private static Connection connection;
    private PreparedStatement preparedStatement;

    private final Connector connector;
    private int index = 1;

    public TutorDaoImpl(){
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
    public void deleteByID(int tutorId) {
        index = 1;
        try {
            String sql = "delete from tutor t where t.tutor_no = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tutorId);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
