package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.inter.VidomistDao;
import com.project.database.entity.Vidomist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VidomistDaoImpl implements VidomistDao {

    private final Connector connector;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int index = 1;

    @Autowired
    public VidomistDaoImpl(Connector connector) {
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

    List<String> setParams(String tutorName, String groupName, String subjectName) {
        tutorName = tutorName == null ? " " : " where t.tutor_name = " + tutorName + " ";
        groupName = groupName == null ? " " : " and g.group_name = " + groupName + " ";
        subjectName = subjectName == null ? " " : " where subject_name = " + tutorName + " ";

        return List.of(tutorName, groupName, subjectName);
    }


    @Override
    public List<Vidomist> findAllByTutorGroupSubject(String tutorName, String groupName, String subjectName, int page, int numberPerPage) {
        List<Vidomist> vidomists = new ArrayList<>();
        index = 1;
        List<String> params = setParams(tutorName, groupName, subjectName);
        try {
            String sql = "select *  " +
                    "from vidomist v  " +
                    "where v.tutor_no in (select t.tutor_no  " +
                    "                     from tutor t  " +
                    params.get(0) +
                    ")  " +
                    "  and v.group_code in (  " +
                    "    select g.group_code  " +
                    "    from \"group\" g  " +
                    "    where g.subject_no in (  " +
                    "          select subject_no  " +
                    "          from subject  " +
                    params.get(2) +
                    "        )  " + params.get(1) + " " +
                    ") limit ? offset ?";
            System.out.println(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return vidomists;
    }


    @Override
    public void deleteById(int vidomistId) {
        index = 1;
        try {
            String sql = "delete from vidomist v where v.vidomist_no=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, vidomistId);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vidomist createVidomist(ResultSet resultSet) throws SQLException {
        Vidomist vidomist = new Vidomist();
        vidomist.setVidomistNo(resultSet.getInt("vidomist_no"));
        vidomist.setTutorNo(resultSet.getInt("tutor_no"));
        vidomist.setPresentCount(resultSet.getInt("present_count"));
        vidomist.setAbsentCount(resultSet.getInt("absent_count"));
        vidomist.setRejectedCount(resultSet.getInt("rejected_count"));
        vidomist.setControlType(resultSet.getString("control_type"));
        vidomist.setExamDate(resultSet.getDate("exam_date"));
        vidomist.setGroupCode(resultSet.getInt("group_code"));
        return vidomist;
    }

    @Override
    public List<Vidomist> findAll(int page, int numberPerPage) {
        List<Vidomist> vidomists = new ArrayList<>();
        index = 1;
        try {
            String sql = "select * from vidomist limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vidomists.add(createVidomist(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vidomists;
    }

    @Override
    public Vidomist findById(int id) {
        Vidomist vidomist = new Vidomist();
        index = 1;
        try {
            String sql = "select * from vidomist where vidomist_no = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vidomist = createVidomist(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vidomist.getVidomistNo() == null
                ? null
                : vidomist;
    }

}
