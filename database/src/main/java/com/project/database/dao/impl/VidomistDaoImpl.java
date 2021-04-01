package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.inter.VidomistDao;
import com.project.database.entity.Vidomist;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VidomistDaoImpl implements VidomistDao {

    private Connection connection;
    private PreparedStatement preparedStatement;

    private final Connector connector;


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


//    public static void main(String[] args) {
//        VidomistDaoImpl vidomistDao = new VidomistDaoImpl(connector);
//        vidomistDao.findAllVidomistsByTutorGroupSubject(null, null, null, 1, 20);

//    }


    List<String> setParams(String tutorName, String groupName, String subjectName) {
        tutorName = tutorName == null ? " " : " where t.tutor_name = " + tutorName + " ";
        groupName = groupName == null ? " " : " and g.group_name = " + groupName + " ";
        subjectName = subjectName == null ? " " : " where subject_name = " + tutorName + " ";

        return List.of(tutorName, groupName, subjectName);
    }

    @Override
    public List<Vidomist> findAllVidomistsByTutorGroupSubject(String tutorName, String groupName, String subjectName, int page, int numberPerPage) {
        List<Vidomist> vidomists = new ArrayList<>();
        int index = 1;

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
}
