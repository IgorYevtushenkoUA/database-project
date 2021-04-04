package com.project.database.dao.impl;

import com.project.database.dao.connector.Connector;
import com.project.database.dao.inter.UserDAO;
import com.project.database.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {

    @Autowired
    public UserDAOImpl(Connector connector) {
        super(connector);
    }


    @Override
    public UserEntity findByUsername(String username) {
        try {
            String sql =
                    "select * " +
                    "from \"user_entity\" where " +
                    "username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(resultSet.getString("username"));
                userEntity.setPassword(resultSet.getString("password"));
                userEntity.setRole(resultSet.getString("role"));
                return userEntity;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return null;
    }
}
