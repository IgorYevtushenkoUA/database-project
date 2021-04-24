package com.project.database.dao.inter;

import com.project.database.entities.UserEntity;

public interface UserDAO {
    UserEntity findByUsername(String username);
}
