package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.connector.ProdConnector;
import com.project.database.dao.inter.GroupDao;
import com.project.database.entity.Group;
import com.project.database.entity.Subject;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    Group createGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setGroupCode(resultSet.getInt("group_code"));
        group.setGroupName(resultSet.getString("group_name"));
        group.setEduYear(resultSet.getString("edu_year"));
        group.setCourse(resultSet.getInt("course"));
        group.setSubjectNo(resultSet.getInt("subject_no"));
        group.setTrim(resultSet.getString("trim"));

        return group;
    }

    @Override
    public List<Group> findAllGroupName(int page, int numberPerPage) {
        List<Group> groups = new ArrayList<>();
        index = 1;
        try {
            String sql = "select distinct(group_name) from \"group\" order by group_name limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groups.add(createGroup(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return groups;
    }

    @Override
    public List<Group> findAllEduYears(int page, int numberPerPage) {
        List<Group> groups = new ArrayList<>();
        index = 1;
        try {
            String sql = "select distinct(edu_year) from \"group\" order by group_name limit ? offset ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(index++, numberPerPage);
            preparedStatement.setInt(index++, (page - 1) * numberPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groups.add(createGroup(resultSet));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return groups;
    }
}
