package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.connector.ProdConnector;
import com.project.database.dao.inter.SubjectDao;
import com.project.database.entity.Student;
import com.project.database.entity.Subject;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubjectDaoImpl implements SubjectDao {

    private static Connection connection;
    private PreparedStatement preparedStatement;

    private final Connector connector;
    private int index = 1;

    public SubjectDaoImpl() {
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
    public void deleteByID(int subjectId) {
        index = 1;
        try {
            String sql = "delete from subject s where s.subject_no = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, subjectId);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Subject createSubject(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectNo(resultSet.getInt("subject_no"));
        subject.setSubjectName(resultSet.getString("subject_name"));
        subject.setEduLevel(resultSet.getString("edu_level"));
        subject.setFaculty(resultSet.getString("faculty"));

        return subject;
    }

    @Override
    public List<Subject> findAll(int page, int numberPerPage) {
        List<Subject> subject = new ArrayList<>();
        index = 1;
        try {
            String sql = "select * from subject order by subject_name limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subject.add(createSubject(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return subject;
    }
}
