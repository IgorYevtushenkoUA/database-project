package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.connector.ProdConnector;
import com.project.database.dao.inter.TutorDao;
import com.project.database.entity.Tutor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TutorDaoImpl implements TutorDao {

    private static Connection connection;
    private PreparedStatement preparedStatement;

    private final Connector connector;
    private int index = 1;

    public TutorDaoImpl() {
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

    Tutor createTutor(ResultSet resultSet) throws SQLException {
        Tutor tutor = new Tutor();
        tutor.setTutorNo(resultSet.getInt("tutor_no"));
        tutor.setTutorName(resultSet.getString("tutor_name"));
        tutor.setTutorSurname(resultSet.getString("tutor_surname"));
        tutor.setTutorPatronymic(resultSet.getString("tutor_patronymic"));
        tutor.setScienceDegree(resultSet.getString("science_degree"));
        tutor.setAcademStatus(resultSet.getString("academ_status"));
        tutor.setPosition(resultSet.getString("position"));

        return tutor;
    }

    @Override
    public List<Tutor> findAll(int page, int numberPerPage) {
        List<Tutor> tutors = new ArrayList<>();
        index = 1;
        try {
            String sql = "select * from tutor order by tutor_surname limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tutors.add(createTutor(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return tutors;
    }
}
